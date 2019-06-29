package application;

public class Player {
	private String name;
	private String nick;
	private String id;
	
	public Player(String name, String nick) {
		this.name = name;
		this.nick = nick;
		this.id = "0";
	}
	
	/*public Player(String id, String nick) {
		this.id = id;
		this.nick = nick;
	}*/
	
	public Player(Integer id, String name, String nick) {
		this.name = name;
		this.nick = nick;
		this.id = String.valueOf(id);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", nick=" + nick + "]";
	}
	
	
}
