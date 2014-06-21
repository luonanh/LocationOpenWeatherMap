package course.examples.Location.GetLocation;

public class WoeidRecord {

	private long id;
	private int woeid;
	private int goenameid;
	
	public WoeidRecord(int woeid, int geonameid) {
		this.woeid = woeid;
		this.goenameid = geonameid;
	}

	public int getWoeid() {
		return woeid;
	}

	public void setWoeid(int woeid) {
		this.woeid = woeid;
	}

	public int getGoenameid() {
		return goenameid;
	}

	public void setGoenameid(int goenameid) {
		this.goenameid = goenameid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
