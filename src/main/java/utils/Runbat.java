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
//                            result = "runbat case2 it works";
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

    private  void dealWithResult(String result) {

    }

    private  void do570Edit(Order order, boolean isSingleEdit) {

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
            result = new StringBuilder();
            result.append("multy order");
            return result.toString();
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
