package utils;

import beans.Order;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.io.*;

public class Runbat {
    public Runbat(){

    }

    public  String dealWithOrder(Order order){
        String result = "";
        if(order != null){
            switch (order.getMachineType()){
                case 2:
                    //2 - 570
                    checkIs570Online();

                    switch (order.getOrderType()){
                        case 1:
                            //1 - 全部查询
                            do570Search(order, false);
                            break;
                        case 2:
                            //2-单个查询
                            result = null;
                            result = do570Search(order, true);
//                            dealWithResult(result);
                            break;
                        case 3:
                            //3-批修改
                            do570Edit(order, false);
                            break;
                        case 4:
                            //4-单个修改
                            do570Edit(order, true);
                            break;
                        case 5:
                            //5-Ping 570 判断是否在线
                            result = null;
                            result = do570Ping(order);
                        default:
                            break;
                    }
                    break;
            }
        }else {
            result = "null, nothing received";
        }
        return result;
    }

    private String do570Ping(Order order) {
        String tempOrder = null;
        StringBuilder result = null;
        String is579Connected = "";
        Runtime runtime = Runtime.getRuntime(); //Runtime.getRuntime()返回当前应用程序的Runtime对象
        Process process = null;  //Process可以控制该子进程的执行或获取该子进程的信息。
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ping -c 4");
            stringBuilder.append(" ");
            stringBuilder.append(order.getIp());

            tempOrder = stringBuilder.toString();
            String[] cmdString = new String[3];
            cmdString[0] = "";
            cmdString[1] = "";
            cmdString[2] = tempOrder;

            System.out.println(tempOrder);
            System.out.println(cmdString);

            process = runtime.exec(tempOrder);
            result = new StringBuilder();
            BufferedInputStream inputStream = new BufferedInputStream(process.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null){
                result.append(line + "\r\n");
            }
            process.waitFor();
            inputStream.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(result != null){
            if(result.toString().contains("Unreachable") || result.toString().contains("100% packet loss")){
                is579Connected  = "disconnect";
            }else {
                is579Connected = "available";
            }
        }

        int i = process.exitValue();  //接收执行完毕的返回值ss
        if (i == 0) {
//        	System.out.println(stringBuffer);
            System.out.println("执行完成.");
        } else {
            System.out.println("执行失败.");
        }

        process.destroy();
        process = null;

        return is579Connected;
    }


    private  void dealWithResult(String result) {

    }

    private  void do570Edit(Order order, boolean isSingleEdit) {

        if(isSingleEdit) {
            String tempOrder = null;
            StringBuilder result = null;
            Runtime runtime = Runtime.getRuntime(); //Runtime.getRuntime()返回当前应用程序的Runtime对象
            Process process = null;  //Process可以控制该子进程的执行或获取该子进程的信息。
            if (isSingleEdit) {
                try {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("snmpset -v 2c -c private");
                    stringBuilder.append(" ");
                    stringBuilder.append(order.getIp());
                    stringBuilder.append(" ");
                    stringBuilder.append(order.getMachine_port());
                    stringBuilder.append(" ");
                    stringBuilder.append(order.getType());
                    stringBuilder.append(" ");
                    stringBuilder.append(order.getValue());

                    tempOrder = stringBuilder.toString();
                    String[] cmdString = new String[3];
                    cmdString[0] = "/bin/sh";
                    cmdString[1] = "-c";
                    cmdString[2] = tempOrder;

                    System.out.println(tempOrder);
                    System.out.println(cmdString);

                    process = runtime.exec(cmdString);

                    process.waitFor();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = process.exitValue();  //接收执行完毕的返回值ss
                if (i == 0) {
//        	System.out.println(stringBuffer);
                    System.out.println("执行完成.");
                } else {
                    System.out.println("执行失败.");
                }

                process.destroy();
                process = null;
            }
        }
    }

    private  String do570Search(Order order, boolean isSingleSearch) {
        String tempOrder = null;
        StringBuilder result = null;
        Runtime runtime = Runtime.getRuntime(); //Runtime.getRuntime()返回当前应用程序的Runtime对象
        Process process = null;  //Process可以控制该子进程的执行或获取该子进程的信息。
        if(isSingleSearch){
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("snmpget -v 2c -c public");
                stringBuilder.append(" ");
                stringBuilder.append(order.getIp());
                stringBuilder.append(" ");
                stringBuilder.append(order.getMachine_port());

                tempOrder = stringBuilder.toString();
                String[] cmdString = new String[3];
                cmdString[0] = "/bin/sh";
                cmdString[1] =  "-c";
                cmdString[2] = tempOrder;

                System.out.println(tempOrder);
                System.out.println(cmdString);

                process = runtime.exec(cmdString);
                result = new StringBuilder();
                BufferedInputStream inputStream = new BufferedInputStream(process.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = reader.readLine()) != null){
                    result.append(line + "\r\n");
                }
                process.waitFor();
                inputStream.close();
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else {
            try {
            StringBuilder multyBuilder = new StringBuilder();
            multyBuilder.append("snmpwalk -v 2c -c public");
            multyBuilder.append(" ");
            multyBuilder.append(order.getIp());
            multyBuilder.append(" ");
            multyBuilder.append(order.getMachine_port());

            tempOrder = multyBuilder.toString();
            String[] multyString = new String[3];
                multyString[0] = "/bin/sh";
                multyString[1] =  "-c";
                multyString[2] = tempOrder;

            System.out.println(tempOrder);
            System.out.println(multyString);

            process = runtime.exec(multyString);
            result = new StringBuilder();
            BufferedInputStream inputStream = new BufferedInputStream(process.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null){
                result.append(line + "\r\n");
            }
            process.waitFor();
            inputStream.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }

        int i = process.exitValue();  //接收执行完毕的返回值ss
        if (i == 0) {
//        	System.out.println(stringBuffer);
            System.out.println("执行完成.");
        } else {
            System.out.println("执行失败.");
        }

        process.destroy();
        process = null;

        return result.toString();
    }

    private  void checkIs570Online() {

    }
}
