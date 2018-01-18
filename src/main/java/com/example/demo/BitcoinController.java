package com.example.demo;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coinbase.api.Coinbase;
import com.coinbase.api.CoinbaseBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;

@RestController
public class BitcoinController {
    Timer timer = new Timer();
    List<Wrap> fun = new ArrayList<>();
    List<String> labels = new ArrayList<>();
    List<Integer> values = new ArrayList<>();
    Coinbase cb = new CoinbaseBuilder()
            .withApiKey(System.getenv("vvdpY0ulMhWhrUWA"), System.getenv("SJSO6EZm6QBekIC9dSM4pG4IOE7FMIcV"))
            .build();

    @RequestMapping("/hello")
    public int hello() {
        try {
            return cb.getSpotPrice(CurrencyUnit.USD).getAmountMajorInt();
        }catch (Exception e){
            return 0;
        }
    }

    @RequestMapping("/start")
    public String start() {
        timer.scheduleAtFixedRate(new TimerTask() { public void run(){
            try {
                values.add(cb.getSpotPrice(CurrencyUnit.USD).getAmountMajorInt());
                labels.add(LocalDate.now().toString());
                //fun.add(new Wrap(cb.getSpotPrice(CurrencyUnit.USD).getAmountMajorInt(), LocalDate.now()));
            } catch (Exception e) {

            }
        }
        },0, 10*1000);

        return "Started";
    }

    @RequestMapping("/stop")
    public String stop() {
        timer.cancel();

        return "Stopped";
    }


    @RequestMapping("/labels")
    public List<String> labels() {
        return labels;
    }

    @RequestMapping("/data")
    public List<Integer> data() {
        return values;
    }



}
