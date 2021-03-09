package pedigree;

public class Event {
	
	public enum Type {
		Birth,
		Death,
		Reproduction
	}
	
	private Type type;
	private Sim subject;
	private double time;
	
	public Event(Type type, Sim subject, double time) {
		this.type = type;
		this.subject = subject;
		this.time = time;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public Sim getSubject() {
		return subject;
	}

	public void setSubject(Sim subject) {
		this.subject = subject;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return Double.toString(time);
	}
	
	
	
	
	public static void main(String[] args) {
		Event e = new Event(Event.Type.Birth, new Sim(null), 2020.0);
	}

	
}
