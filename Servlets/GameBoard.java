package cs4640work;

import java.util.List;

public class GameBoard {
	
	private String username;
	private String gameName;
	private List<QAObject> qaObjects;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public List<QAObject> getQAObjects() {
		return qaObjects;
	}
	public void setQAObjects(List<QAObject> qaObjects) {
		this.qaObjects = qaObjects;
	}
	
}
