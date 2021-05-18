package configuration;

import random.MersenneTwisterFast;

public enum Configuration {
    instance;

    public String fileSeparator = System.getProperty("file.separator");
    public String userDirectory = System.getProperty("user.dir");

    public String dataDirectory = userDirectory + fileSeparator + "data" + fileSeparator;
    public String dataFilePath = dataDirectory + "tsp280.txt";
    public MersenneTwisterFast randomGenerator = new MersenneTwisterFast(System.currentTimeMillis());
}