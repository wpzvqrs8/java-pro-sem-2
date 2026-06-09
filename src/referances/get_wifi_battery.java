package referances;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class SystemStats {

    public static void main(String[] args) throws Exception {

        while (true) {

            System.out.println(
                    "Battery: " + getBattery() + "%"
            );

            System.out.println(
                    "WiFi: " + getWifi() + "%"
            );
            System.out.println(
                    "Charging: " + isCharging() + "%"
            );

            System.out.println("---------------");

            Thread.sleep(3000);
        }
    }

    static String getBattery() throws Exception {

        Process p = Runtime.getRuntime().exec(
                "powershell.exe (Get-WmiObject Win32_Battery).EstimatedChargeRemaining"
        );

        BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream())
        );

        return br.readLine().trim();
    }

    static String getWifi() throws Exception {

        Process p = Runtime.getRuntime().exec(
                "netsh wlan show interfaces"
        );

        BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream())
        );

        String line;

        while ((line = br.readLine()) != null) {

            line = line.trim();

            if (line.startsWith("Signal")) {
                return line.split(":")[1].trim();
            }
        }

        return "0%";
    }
    static String isCharging() throws Exception {

        Process p = Runtime.getRuntime().exec(
                "powershell.exe (Get-WmiObject Win32_Battery).BatteryStatus"
        );

        BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream())
        );

        String status = br.readLine().trim();

        // 2 = charging, 1 = discharging
        if (status.equals("2")) return "Yes";
        return "No";
    }
}