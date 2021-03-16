package pedigree;

import java.util.NoSuchElementException;
import java.util.Random;

public class Pedigree {
	
	private PQ<Sim> population;
	private PQ<Event> eventQ;
	private Random rand;
	private AgeModel ageModel;
	private double rate;
	private double fidelity;

	public void simulate(int n, int maxTime) {
		
		//Setting founding population
		population = new PQ(n, new DeathComparator());

		//The event queue
		eventQ = new PQ(new EventComparator());
		
		rand = new Random();
		ageModel = new AgeModel();
		
		//TODO: check this
		rate = 2 / ageModel.expectedParenthoodSpan(Sim.MIN_MATING_AGE_F, Sim.MAX_MATING_AGE_F);
		fidelity = 0.9;

		initPopulation(n);

		//Events
		while (!eventQ.isEmpty()) {

			Event E = eventQ.deleteMin();
			
			//System.out.println(eventQ.size());

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
		
		int a = 0;


		//TODO for B part
		//copy population
		//Heapify according to BirthComparator
		//Note: parents are saved in attributes of a Sim
	}
	
	private void initPopulation(int n) {
		for(int i = 0; i < n; i++) {
			Sim s = new Sim(null);
			s.randomizeSex(rand);
					
			eventQ.insert(new Event(Event.Type.Birth, s, 0.0));

		}
	}


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
	
	private void handleDeathEvent(Event E) {
	
		//System.out.println(population.peek().getDeathTime() + ", " + E.getTime() );
		
		if(population.peek().getDeathTime() <= E.getTime()) {
			
			population.deleteMin();

		}
		else {
			//TODO: Handle this
			System.out.println("Problème heap de population");
		}
	}
	
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
	
	private Sim findMate(Event E, double acceptanceRate) {
		
		int i = 0;
		final int LIMIT = 10000000;//To make sure there is a mate (that the while will stop)
		
		Sim mate;
		
		do {
			int index = 1+rand.nextInt(population.size()-1);
			
			mate = population.get(index);
			
			i++;
		} while((!mate.isMatingAge(E.getTime())) 
				&& (mate.getSex() != Sim.Sex.M) 
				&& (i < LIMIT) 
				&& (!mate.isInARelationship(E.getTime()) || rand.nextDouble() > acceptanceRate));
		
		if(i == LIMIT) {
			throw new NoSuchElementException();
		}
		
		//Match the 2 mates
		mate.setMate(E.getSubject());
		E.getSubject().setMate(mate);

		return mate;
		
	}
	
	private void mate(Sim mother, Sim father, Event E) {
		
		Sim child = new Sim(mother, father, E.getTime());
		child.randomizeSex(rand);
		
		eventQ.insert(new Event(Event.Type.Birth, child, E.getTime()));
	}

	
	

	public static void main(String[] args) {

		//Args
		int n = Integer.parseInt(args[0]);
		int maxTime = Integer.parseInt(args[1]);
		
		Pedigree p = new Pedigree();

		p.simulate(n, maxTime);
		
	}

}
