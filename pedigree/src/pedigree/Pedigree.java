package pedigree;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Random;

public class Pedigree {

	/**
	 * The main program of the project
	 * */
	public static void main(String[] args) {

		//Args
		int n = Integer.parseInt(args[0]);
		int maxTime = Integer.parseInt(args[1]);

		//Simulate
		Pedigree p = new Pedigree();
		p.simulate(n, maxTime);
	}

	private PQ<Sim> population;
	private PQ<Sim> malePopulation;
	private PQ<Sim> femalePopulation;
	private HashMap<Sim,Double> maleAncestorMap;
	private HashMap<Sim,Double> femaleAncestorMap;
	private PQ<Event> eventQ;

	private Random rand;
	private AgeModel ageModel;

	private double rate;
	private double fidelity;

	/**
	 * Simulation.
	 * @param n, the size of the initial population
	 * @param maxTime, the duration of the simulation
	 * */
	public void simulate(int n, int maxTime) {

		//Setting founding population
		population = new PQ(n, new DeathComparator());

		//The event queue
		eventQ = new PQ(new EventComparator());

		rand = new Random();
		ageModel = new AgeModel();

		//Our reproduction rate and our fidelity factor
		rate = 2 / ageModel.expectedParenthoodSpan(Sim.MIN_MATING_AGE_F, Sim.MAX_MATING_AGE_F);
		fidelity = 0.9;

		initPopulation(n);

		//Variable used to sample every 100 years
		int sample = 0;

		//Events
		while (!eventQ.isEmpty()) {

			Event E = eventQ.deleteMin();

			if(E.getTime() > sample) {
				System.out.println(E.getTime() + "," + population.size());
				sample += 100;
			}

			if (E.getTime() > maxTime) break;

			if (E.getSubject().getDeathTime() > E.getTime()){

				if(E.getType() == Event.Type.Birth) {

					handleBirthEvent(E);
				}
				else if(E.getType() == Event.Type.Reproduction) {

					handleReproductionEvent(E);
				}
			}
			else {

				if(E.getType() == Event.Type.Death) {

					handleDeathEvent(E);
				}
			}
		}
		//We're not making a copy, but might want to change that in the future
		coalescencePoints(population);
	}

	/**
	 * Prints the coalescence points of our population
	 * @param population, our population
	 * */
	private void coalescencePoints(PQ<Sim> population) {

		//Create new priority queues for our male and female population of initiated to roughly half of population size
		malePopulation = new PQ(population.size()/2,new BirthComparator());
		femalePopulation = new PQ(population.size()/2,new BirthComparator());

		//HashMaps containing males / females ancestors
		maleAncestorMap = new HashMap<>();
		femaleAncestorMap = new HashMap<>();

		//Iterate through our population to seperate between males and females
		while (!population.isEmpty()) {

			Sim s = population.deleteMin();

			if (s.getSex() == Sim.Sex.M) {

				malePopulation.insert(s);
			}
			else {

				femalePopulation.insert(s);
			}
		}

		int numberOfMen = malePopulation.size();
		int numberOfWomen = femalePopulation.size();

		System.out.println("Men ancestors");

		//Iterating through all our men
		while (numberOfMen > 0) {
			Sim s = malePopulation.deleteMin();
			Sim father = s.getFather();

			//If the father is not a founder and isn't already in our map, we add him to our population and our ancestorMap
			if(father != null && !maleAncestorMap.containsKey(father)) {
				maleAncestorMap.put(father, father.getBirthTime());
				malePopulation.insert(father);
			}
			//Else we've found a lineage and we decrease the number of men we need to go through (because one line is closed off)
			else {
				System.out.println(s.getBirthTime() + "," + numberOfMen);
				numberOfMen--;
			}
		}

		System.out.println("Women ancestors");

		//Same thing as men, but for women and their mothers.
		while (numberOfWomen > 0) {
			Sim s = femalePopulation.deleteMin();
			Sim mother = s.getMother();

			if(mother != null && !femaleAncestorMap.containsKey(mother)) {
				femaleAncestorMap.put(mother,mother.getBirthTime());
				femalePopulation.insert(mother);
			}
			else {
				//System.out.println(s.getBirthTime() + "," + femaleAncestorMap.size());
				numberOfWomen--;
			}
		}
	}

	private int countSex(Sim.Sex s) {

		int count = 0;
		Object[] populationArray = population.toArray();

		for (int i = 0; i < populationArray.length; i++) {

			if(((Sim) populationArray[i]).getSex() == s) {

				count++;
			}
		}
		return count;
	}


	/**
	 * Initializes the population
	 * @param n, the size of the population
	 * */
	private void initPopulation(int n) {
		for(int i = 0; i < n; i++) {

			Sim s = new Sim(null);
			s.randomizeSex(rand);
			eventQ.insert(new Event(Event.Type.Birth, s, 0.0));
		}
	}

	/**
	 * Handles a given birth event
	 * @param E, the event
	 * */
	private void handleBirthEvent(Event E) {

		Sim s = E.getSubject();

		//Death
		s.setDeathTime(E.getTime() + ageModel.randomAge(rand));
		eventQ.insert(new Event(Event.Type.Death, s, s.getDeathTime()));

		//Reproduction
		if(s.getSex() == Sim.Sex.F) {

			//TODO: check this
			eventQ.insert(new Event(Event.Type.Reproduction, s, E.getTime() + ageModel.randomWaitingTime(rand, rate)));
		}
		//Add the Sim to the population
		population.insert(E.getSubject());
	}

	/**
	 * Handles a given death event
	 * @param E, the event
	 * */
	private void handleDeathEvent(Event E) {

		if(population.peek().getDeathTime() <= E.getTime()) {

			population.deleteMin();
		}
		else {

			//TODO: Handle this
			System.out.println("Problème heap de population");
		}
	}

	/**
	 * Handles a given reproduction event
	 * @param E, the event
	 * */
	private void handleReproductionEvent(Event E) {

		if(E.getSubject().isMatingAge(E.getTime())) {

			if(E.getSubject().isInARelationship(E.getTime())) {

				Sim father = E.getSubject().getMate();
				if(rand.nextDouble() > fidelity) {

					//Change mate
					father = findMate(E, 1);
				}
				mate(E.getSubject(), father, E);
			}
			else {

				Sim father = findMate(E, 1-fidelity);
				mate(E.getSubject(), father, E);
			}
		}

		eventQ.insert(new Event(Event.Type.Reproduction, E.getSubject(), E.getTime() + ageModel.randomWaitingTime(rand, rate)));
	}

	/**
	 * Finds a mate to the subject associated with the given event
	 * @param E, the event for which we want to find a mate for the subject
	 * @param acceptanceRate, a rate that will influence the choosen mate
	 * @return the choosen mate
	 * */
	private Sim findMate(Event E, double acceptanceRate) {

		int i = 0;

		//To make sure there is a mate (that the while will stop)
		final int LIMIT = population.size() * 100;

		Sim mate;

		while (true) {

			int index = rand.nextInt(population.size());
			mate = population.get(index);
			i++;

			if((mate.isMatingAge(E.getTime())) && (mate.getSex() == Sim.Sex.M) && ((!mate.isInARelationship(E.getTime())) || rand.nextDouble() > acceptanceRate)) {
				break;
			}
			//We don't want to loop to infinity in the really really really rare chance that no suitable males exist in the population, so we set arbitrary
			//limit and throw exception if it's reached.
			else if (i > LIMIT) {
				throw new NoSuchElementException();
			}
		}

		//Match the 2 mates
		mate.setMate(E.getSubject());
		E.getSubject().setMate(mate);

		return mate;
	}

	/**
	 * Mates 2 sims
	 * @param mother, the mother
	 * @param father, the father
	 * @param E, the event when the mating happens
	 * */
	private void mate(Sim mother, Sim father, Event E) {

		Sim child = new Sim(mother, father, E.getTime());
		child.randomizeSex(rand);

		eventQ.insert(new Event(Event.Type.Birth, child, E.getTime()));
	}

	//Unused for now
//	private int countSex(Sim.Sex s) {
//
//		int count = 0;
//		Object[] populationArray = population.toArray();
//
//		for (int i = 0; i < populationArray.length; i++) {
//
//			if(((Sim) populationArray[i]).getSex() == s) {
//
//				count++;
//			}
//		}
//		return count;
//	}
}
