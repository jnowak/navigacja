package mapy.cyfrowe;

public enum TransportMode
{
	driving("driving"),
	walking("walking"),
	bicycling("bicycling");
	
	private String name;
	
	private TransportMode(String name)
	{
		this.name = name;
	}
	
	public String toString()
	{
		return name;
	}
}
