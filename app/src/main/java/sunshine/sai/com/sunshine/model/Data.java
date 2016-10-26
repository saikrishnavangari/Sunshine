package sunshine.sai.com.sunshine.model;

import java.util.ArrayList;

/**
 * Created by krrish on 17/10/2016.
 */

public class Data {
    ArrayList<list> list;

    public ArrayList<Data.list> getList() {
        return list;
    }

    public void setList(ArrayList<Data.list> list) {
        this.list = list;
    }

    public static class list{
        Temp temp;
        ArrayList <weather> weather;

        public Temp getTemp() {
            return temp;
        }

        public void setTemp(Temp temp) {
            this.temp = temp;
        }

        public ArrayList<Data.weather> getWeather() {
            return weather;
        }

        public void setWeather(ArrayList<Data.weather> weather) {
            this.weather = weather;
        }
    }

    public static  class Temp{
        double min;
        double max;

        public int getMin() {
            return (int)min;
        }

        public int getMax() {
            return (int)max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public void setMin(double min) {
            this.min = min;
        }
    }
    public static  class weather{

        String main;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }
    }
}
