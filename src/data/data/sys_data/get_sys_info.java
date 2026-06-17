package data.data.sys_data;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class get_sys_info {

    public String getBattery() throws Exception {

        Process p = Runtime.getRuntime().exec(
                "powershell.exe (Get-WmiObject Win32_Battery).EstimatedChargeRemaining"
        );

        BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream())
        );
        int battery = Integer.parseInt(br.readLine());
        String icon;
         boolean is__charging = isCharging();
        if (!is__charging){

            switch ((Math.min(battery, 100) / 10)+1)
            {

                case 0  -> icon = "\uF5F2";
                case 1  -> icon = "\uF5F3";
                case 2  -> icon = "\uF5F4";
                case 3  -> icon = "\uF5F5";
                case 4  -> icon = "\uF5F6";
                case 5  -> icon = "\uF5F7";
                case 6  -> icon = "\uF5F8";
                case 7  -> icon =  "\uF5F9";
                case 8  -> icon = "\uF5FA";
                case 9  -> icon = "\uF5FB";
                default ->icon = "\uF5FC";

            }

        }
        else{
            switch ((Math.min(battery, 100) / 10)+1){
                case 0  ->icon = "\uF5FD";
                case 1  ->icon = "\uF5FE";
                case 2  ->icon = "\uF5FF";
                case 3  -> icon ="\uF600";
                case 4  ->icon = "\uF601";
                case 5  ->icon = "\uF602";
                case 6  -> icon ="\uF603";
                case 7  ->icon = "\uF604";
                case 8  -> icon ="\uF605";
                case 9  ->icon = "\uF606";
                default ->icon = "\uF607";
            }
        }

        p.waitFor();
        p.destroy();
        return icon;
    }

    public String getWifi() throws Exception {

        Process p = Runtime.getRuntime().exec(
                "netsh wlan show interfaces"
        );

        BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream())
        );
//dummy

        String line,wifi="";

        while ((line = br.readLine()) != null) {

            line = line.trim();

            if (line.startsWith("Signal")) {
                wifi=  line.split(":")[1].trim();
            }
        }
        wifi = wifi.replace("%","");
        String wifiIcon = switch (Math.min(4, Integer.parseInt(wifi) / 25)) {
            case 0 -> "\uEC40";
            case 1 -> "\uEC3C";
            case 2 -> "\uEC3D";
            case 3 -> "\uEC3E";
            default -> "\uEC3F"; // 100%
        };

        p.waitFor();
        p.destroy();
//        System.out.println("\uEC3C \uEC3D \uEC3E \uEC3F");
        return wifiIcon;
    }
    public boolean isCharging() throws Exception {

        Process p = Runtime.getRuntime().exec(
                "powershell.exe (Get-WmiObject Win32_Battery).BatteryStatus"
        );

        BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream())
        );

        String status = br.readLine().trim();

        // 2 = charging, 1 = discharging

        p.waitFor();
        p.destroy();
        return !status.equals("1");
    }
}
