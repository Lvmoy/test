package servlets;

import beans.Order;
import beans.Result;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import utils.Runbat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/3/31 0031.
 */
public class RunbatServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result = "";
        String result_final = "";
        req.setCharacterEncoding("utf-8");
        try {
            if(req != null){
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(req.getInputStream(), "utf-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String line ;
                while ((line = bufferedReader.readLine())!= null){
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                result = stringBuilder.toString();

                System.out.println("result = " + result);

            }else {
                System.out.println("req : " + "post -> req = null");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if(! result.equals("")){
            Order order = new Gson().fromJson(result, Order.class);
            System.out.println("order = " + order.toString());

            Runbat runbat = new Runbat();
            result_final = runbat.dealWithOrder(order);
            //result

            if(resp != null){
                resp.setCharacterEncoding("utf-8");
                PrintWriter printWriter = resp.getWriter();
                Result result1 = new Result(result_final);
                Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
                String json = gson.toJson(result1);

                System.out.println(json);
                //not sure
                printWriter.write(json);
                printWriter.print(json);

                printWriter.flush();
                printWriter.close();
            }else {
                System.out.println("resp = null");

            }
        }else {
            System.out.println("rsq: result = null");
        }

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
