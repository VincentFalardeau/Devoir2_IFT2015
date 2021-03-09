package pedigree;

import java.util.Random;

public class Pedigree {

	public static void main(String[] args) {

		//Args
		int n = Integer.parseInt(args[0]);
		int maxTime = Integer.parseInt(args[1]);

		//Setting founding population
		Heap<Sim> population = new Heap(n, new DeathComparator());

		//The event queue
		Heap<Event> eventQ = new Heap(new EventComparator());

		Random rand = new Random();
		AgeModel ageModel = new AgeModel();

		for(int i = 0; i < n; i++) {
			Sim s;

			if(rand.nextInt(1) == 0) {
				s = new Sim(Sim.Sex.F);

			}
			else {
				s = new Sim(Sim.Sex.M);
			}
			eventQ.insert(new Event(Event.Type.Birth, s, 0.0));

		}

		//TODO: check this
		Double rate = 2 / ageModel.expectedParenthoodSpan(Sim.MIN_MATING_AGE_F, Sim.MAX_MATING_AGE_F);


		//Events
		while (!eventQ.isEmpty()) {

			Event E = eventQ.deleteMin();

			if (E.getTime() > maxTime) break;

			if (E.getSubject().getDeathTime() > E.getTime()){
				
				if(E.getType() == Event.Type.Birth) {

					Sim s = E.getSubject();

					//Death
					s.setDeathTime(ageModel.randomAge(rand));
					eventQ.insert(new Event(Event.Type.Death, s, E.getTime() + s.getDeathTime()));

					//Reproduction
					if(s.getSex() == Sim.Sex.F) {
						//TODO: check this
						eventQ.insert(new Event(Event.Type.Reproduction, s, E.getTime() + ageModel.randomWaitingTime(rand, rate)));
					}

					//Add the Sim to the population
					population.insert(E.getSubject());

				}
				else if(E.getType() == Event.Type.Death) {
					if(!(population.deleteMin().getDeathTime() == E.getTime())) {

						//TODO: Handle this
						System.out.println("Problème heap de population");
					}
				}
				else if(E.getType() == Event.Type.Reproduction) {

					if(E.getSubject().isMatingAge(E.getTime())) {
						//TODO: finish this
						//Create new sim
						//Choose partner according to the rules
						//TODO: set parents
						//Sim s = new Sim(parents)
						//birth event
					}
					else {
						eventQ.insert(new Event(Event.Type.Reproduction, E.getSubject(), E.getTime() + ageModel.randomWaitingTime(rand, rate)));
					}

				}

			}
		}
		
		
		//TODO for B part
		//copy population
		//Heapify according to BirthComparator
		//Note: parents are saved in attributes of a Sim
	}

}
