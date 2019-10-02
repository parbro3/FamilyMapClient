package data;

/**
 * Created by Parker on 4/13/18.
 */

public class Settings {

    /**
     * settings instance. static to be accessed anywhere in the code.
     */
    private static Settings settings_instance = null;

    private String mapType;
    private String lifeStoryLinesColor;
    private String familyTreeLinesColor;
    private String spouseLinesColor;

    private Boolean lifeStoryLinesOn;
    private Boolean familyTreeLinesOn;
    private Boolean spouseLinesOn;


    public Settings(){}

    public void initialize()
    {
        mapType = "Normal";
        lifeStoryLinesColor = "Red";
        familyTreeLinesColor = "Green";
        spouseLinesColor = "Blue";
        lifeStoryLinesOn = true;
        familyTreeLinesOn = true;
        spouseLinesOn = true;
    }

    /**
     *
     * @return
     */
    public static Settings getInstance()
    {
        if(settings_instance == null)
            settings_instance = new Settings();
        return settings_instance;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public String getLifeStoryLinesColor() {
        return lifeStoryLinesColor;
    }

    public void setLifeStoryLinesColor(String lifeStoryLinesColor) {
        this.lifeStoryLinesColor = lifeStoryLinesColor;
    }

    public String getFamilyTreeLinesColor() {
        return familyTreeLinesColor;
    }

    public void setFamilyTreeLinesColor(String familyTreeLinesColor) {
        this.familyTreeLinesColor = familyTreeLinesColor;
    }

    public String getSpouseLinesColor() {
        return spouseLinesColor;
    }

    public void setSpouseLinesColor(String spouseLinesColor) {
        this.spouseLinesColor = spouseLinesColor;
    }

    public Boolean getLifeStoryLinesOn() {
        return lifeStoryLinesOn;
    }

    public void setLifeStoryLinesOn(Boolean lifeStoryLinesOn) {
        this.lifeStoryLinesOn = lifeStoryLinesOn;
    }

    public Boolean getFamilyTreeLinesOn() {
        return familyTreeLinesOn;
    }

    public void setFamilyTreeLinesOn(Boolean familyTreeLinesOn) {
        this.familyTreeLinesOn = familyTreeLinesOn;
    }

    public Boolean getSpoueLinesOn() {
        return spouseLinesOn;
    }

    public void setSpoueLinesOn(Boolean spoueLinesOn) {
        this.spouseLinesOn = spoueLinesOn;
    }
}
