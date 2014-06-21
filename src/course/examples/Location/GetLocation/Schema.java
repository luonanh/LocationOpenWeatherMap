package course.examples.Location.GetLocation;

public class Schema {
	public static class Item {
		public static class Cols {
			public static final String ID = "_id";
			public static final String NAME = "name";
			public static final String BRAND = "brand";
			public static final String DESCRIPTION = "description";
			public static final String IMAGE_LINK = "imageLink";
			public static final String CROP_IMAGE_LINK = "cropImageLink";
			public static final String COLOR = "color";	
			public static final String TEMPERATUTRE_MIN = "TempMin";
			public static final String TEMPERATUTRE_MAX = "TempMax";
			public static final String CATEGORY = "category";
			public static final String AGE = "age";
			public static final String MATERIAL = "material";
		}
	}
	
	public static class UserProfile {
		public static class Cols {
			public static final String ID = "_id";
			public static final String USR = "username";
			public static final String PWD = "password";
			public static final String SEX = "sex";
			public static final String ZIP = "zip";
			public static final String LAUNDRY_SCHEDULE = "laundrySchedule";
			public static final String LAUNDRY_DAY = "laundryDay";
		}
	}
	
	public static class WoeidTable {
		public static class Cols {
			public static final String ID = "_id";
			public static final String WOEID = "woeid";
			public static final String GEONAMEID = "geonameId";
		}
	}
}
