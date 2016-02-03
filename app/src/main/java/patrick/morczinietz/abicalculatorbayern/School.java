package patrick.morczinietz.abicalculatorbayern;


public class School {
	private String stadt, name, art;

	public School(String stadt,String name, String art) {
		this.stadt = stadt;
		this.name = name;
		this.art = art;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStadt() {
		return stadt;
	}

	public void setStadt(String stadt) {
		this.stadt = stadt;
	}

	public String getArt() {
		return art;
	}

	public void setArt(String art) {
		this.art = art;
	}

}
