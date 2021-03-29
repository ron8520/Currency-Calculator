package W14B_G4_Assignment1.view;

import W14B_G4_Assignment1.model.Calculator;
import W14B_G4_Assignment1.model.Currency;
import W14B_G4_Assignment1.model.JsonReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class AdminController implements Initializable{
    private UIWindow window;
    private String date;

    @FXML
    private Button bthome, btsetting, btclose, bthistory, btcalculator, replace, change, add;

    @FXML
    private Pane pnhistory, pncalculator, pnhome, pnsetting;

    @FXML
    private Label username, fromCur1, fromCur2, fromCur3, fromCur4, toCurNames, cur1List, cur2List, cur3List, cur4List, med, avg, max, min, std;

    @FXML
    private ChoiceBox fromChoice, toChoice, fromChoiceHistory, toChoiceHistory, fromDate, toDate, replaceFrom, replaceTo, changeFrom, changeTo, symbol, relateCurrency;

    @FXML
    private TextField fromAmount, toAmount, newRate, name, relateRate;

    @FXML
    private ListView<String> historyList;

    private ObservableList<String> items = FXCollections.observableArrayList ();

    @Override
    public void initialize(URL url, ResourceBundle resources){

    }

    public AdminController(UIWindow window){
        this.window = window;
    }

    @FXML
    public void handleMouseEvent(ActionEvent event){
        if(event.getSource() == bthome){
            pnhome.toFront();
        }
        if(event.getSource() == btcalculator){
            pncalculator.toFront();
        }
        if(event.getSource() == bthistory){
            pnhistory.toFront();
        }
        if(event.getSource() == btsetting){
            pnsetting.toFront();
        }

    }


    @FXML
    public void choiceBoxEvent(ActionEvent event){
        String fromValue = fromAmount.getText();
        String toValue = toAmount.getText();
        if (!fromValue.matches("\\d*")||!toValue.matches("\\d*")) {
            fromAmount.setText(fromValue.replaceAll("[^\\d.]", ""));
            fromValue = fromAmount.getText();
            toAmount.setText(toValue.replaceAll("[^\\d.]", ""));
            toValue = toAmount.getText();
        }
        if(fromChoice.getValue() != null && toChoice.getValue() != null){
            String from = fromChoice.getValue().toString();
            String to = toChoice.getValue().toString();
            if(Currency.getCurrency(from) != null && Currency.getCurrency(to) != null){
                double rate = Currency.getCurrency(from).getCurrencyRate(Currency.getCurrency(to).getCurrencyID());
                if(event.getSource() == fromAmount){
                    toAmount.setText(Double.valueOf(Double.parseDouble(fromValue)*rate).toString());
                }
                if(event.getSource() == toAmount){
                    fromAmount.setText(Double.valueOf(Double.parseDouble(toValue)*rate).toString());
                }
            }
        }
    }

    @FXML
    public void historyEvent(ActionEvent event){
        if(fromChoiceHistory.getValue() != null && toChoiceHistory.getValue() != null && fromDate.getValue() != null && toDate.getValue() != null){
            historyList.getItems().clear();
            items.clear();
            String fromCurrency = fromChoiceHistory.getValue().toString();
            String toCurrency = toChoiceHistory.getValue().toString();
            String startTime = fromDate.getValue().toString();
            String endTime = toDate.getValue().toString();
            Integer startDay = Integer.parseInt(startTime.substring(8,10));
            Integer endDay = Integer.parseInt(endTime.substring(8,10));
            avg.setText("");
            med.setText("");
            max.setText("");
            min.setText("");
            std.setText("");
            if(startDay <= endDay && !fromCurrency.equals(toCurrency)) {
                Calculator result = new Calculator(startTime, endTime, fromCurrency, toCurrency);
                List<String> transaction = result.getTransactions();
                for (int i = 0; i < transaction.size(); i++) {
                    items.add(transaction.get(i));
                }
                historyList.getItems().addAll(items);
                if (fromChoiceHistory.getValue().equals(toChoiceHistory.getValue())) {
                    avg.setText("1");
                    med.setText("1");
                    max.setText("1");
                    min.setText("1");
                    std.setText("1");
                } else {
                    avg.setText(String.format("%.3f", result.average()));
                    med.setText(String.format("%.3f", result.median()));
                    max.setText(String.format("%.3f", result.maximum()));
                    min.setText(String.format("%.3f", result.minimum()));
                    std.setText(String.format("%.3f", result.stdDeviation()));
                }
            }
        }
    }

    @FXML
    public void replacePopular(){
        if(replaceFrom.getValue()!=null && replaceTo != null){
            int fromID = Currency.getCurrency(replaceFrom.getValue().toString()).getCurrencyID();
            int toID = Currency.getCurrency(replaceTo.getValue().toString()).getCurrencyID();
            Currency.setPopular(toID,fromID);
            this.updateSettingPane();
            this.updatePopularTable(this.date);
        }
    }

    @FXML
    public void changeRate(){
        if(changeFrom.getValue()!=null && changeTo != null && newRate.getText() != null){
            if(!changeFrom.getValue().toString().equals(changeTo.getValue().toString())){
                int fromID = Currency.getCurrency(changeFrom.getValue().toString()).getCurrencyID();
                int toID = Currency.getCurrency(changeTo.getValue().toString()).getCurrencyID();
                String newValue = newRate.getText();
                if (!newValue.matches("\\d*")||!newValue.matches("\\d*")) {
                    fromAmount.setText(newValue.replaceAll("[^\\d.]", ""));
                    newValue = fromAmount.getText();
                }
                Currency.changeExchangeRate(fromID,toID,Double.parseDouble(newValue));
            }

        }
        this.updatePopularTable(this.date);
        this.updateSettingPane();
        this.updateCalculatePane();

    }

    @FXML
    public void addCurrency(){
        if(relateCurrency.getValue() != null && symbol.getValue()!= null){
            String relateName = relateCurrency.getValue().toString();
            int relateID = Currency.getCurrency(relateName).getCurrencyID();
            String newName = name.getText();
            newName = newName.replaceAll("[^A-Za-z]","");
            name.setText(newName);
            if(newName.length() >=3){
                newName = newName.substring(0,3).toUpperCase();
                if (Currency.getCurrency(newName) == null){
                    String rate = relateRate.getText();
                    if (!rate.matches("\\d*")||!rate.matches("\\d*")) {
                        fromAmount.setText(rate.replaceAll("[^\\d.]", ""));
                        rate = fromAmount.getText();
                    }
                    new Currency(newName,symbol.getValue().toString(),relateID,Double.parseDouble(rate));
                }
            }
        }
        updateSettingPane();
        updatePopularTable(this.date);
        updateCalculatePane();
    }

    public void updatePopularTable(String date){
        String names = "";
        List<Integer> popular = Currency.getPopularCurrencies();
        List<Label> table = Arrays.asList(cur1List,cur2List,cur3List,cur4List);
        List<Label> fromNames = Arrays.asList(fromCur1,fromCur2,fromCur3,fromCur4);
        for(int i = 0; i < 4; i ++){
            names += String.format("%-12s", Currency.getName(popular.get(i))+Currency.getSymbol(popular.get(i)));
            String output = "";
            for(int j = 0; j < 4; j ++){
                double rate = Currency.getCurrency(popular.get(i)).getCurrencyRate(popular.get(j));
                if(i==j){
                    output += String.format("%-15s","-");
                }else{
                    String formatRate = String.format("%.3f",rate);
                    output += String.format("%-15s",rate + Currency.getStatus(popular.get(i), popular.get(j),date));
                }
            }
            table.get(i).setText(output);
            fromNames.get(i).setText(Currency.getName(popular.get(i))+Currency.getSymbol(popular.get(i)));
        }
        toCurNames.setText(names);
    }

    public void updateCalculatePane(){
        fromChoice.setStyle("-fx-font: 18 arial;");
        toChoice.setStyle("-fx-font: 18 arial;");

        fromChoice.getItems().clear();
        toChoice.getItems().clear();

        for(int i = 0; i < Currency.getCurrencyCount(); i ++){
            fromChoice.getItems().add(Currency.getName(i));
            toChoice.getItems().add(Currency.getName(i));
        }

        fromChoice.setValue(Currency.getName(0));
        toChoice.setValue(Currency.getName(0));
        fromChoiceHistory.setValue(Currency.getName(0));
        toChoiceHistory.setValue(Currency.getName(1));
    }

    public void updateHistoryPane(String filename){
        fromChoiceHistory.setStyle("-fx-font: 18 arial;");
        toChoiceHistory.setStyle("-fx-font: 18 arial;");
        fromDate.setStyle("-fx-font: 18 arial;");
        toDate.setStyle("-fx-font: 18 arial;");

        fromChoiceHistory.getItems().clear();
        toChoiceHistory.getItems().clear();
        fromDate.getItems().clear();
        toDate.getItems().clear();

        for(int i = 0; i < Currency.getCurrencyCount(); i ++){
            fromChoiceHistory.getItems().add(Currency.getName(i));
            toChoiceHistory.getItems().add(Currency.getName(i));
        }

        fromChoiceHistory.setValue(Currency.getName(0));
        toChoiceHistory.setValue(Currency.getName(1));

        JsonReader dataReader = new JsonReader(filename);
        List<String> dates = dataReader.getDate();
        for(int i = 0; i < dates.size(); i ++){
            fromDate.getItems().add(dates.get(i));
            toDate.getItems().add(dates.get(i));
        }
        toDate.setValue(dates.get(0));
        toDate.setValue(dates.get(1));
        historyList.setStyle("-fx-font: 20 arial;");
    }

    public void updateSettingPane(){
        List<Integer> popular = Currency.getPopularCurrencies();
        replaceFrom.setStyle("-fx-font: 18 arial;");
        replaceTo.setStyle("-fx-font: 18 arial;");
        replaceFrom.getItems().clear();
        replaceTo.getItems().clear();;
        for(int i = 0; i < 4; i ++){
            replaceFrom.getItems().add(Currency.getName(popular.get(i)));
        }
        for(int i = 0; i < Currency.getCurrencyCount(); i ++){
            if(popular.contains(i)){
                continue;
            }
            replaceTo.getItems().add(Currency.getName(i));
        }


        changeFrom.setStyle("-fx-font: 18 arial;");
        changeTo.setStyle("-fx-font: 18 arial;");
        relateCurrency.setStyle("-fx-font: 18 arial;");
        changeFrom.getItems().clear();
        changeTo.getItems().clear();
        relateCurrency.getItems().clear();
        for(int i = 0; i < Currency.getCurrencyCount(); i ++){
            changeFrom.getItems().add(Currency.getName(i));
            changeTo.getItems().add(Currency.getName(i));
            relateCurrency.getItems().add(Currency.getName(i));
        }

        symbol.setStyle("-fx-font: 18 'Times New Roman';");
        symbol.getItems().clear();
        symbol.getItems().add("\u0024");
        symbol.getItems().add("\u20AC");
        symbol.getItems().add("\u00A3");
        symbol.getItems().add("\u00A5");
        symbol.getItems().add("\u0E3F");


    }

    public void updateTable(String filename, String date){
        this.date = date;
        this.updatePopularTable(this.date);
        this.updateCalculatePane();
        this.updateHistoryPane(filename);
        this.updateSettingPane();
    }




    @FXML
    public void exit(){
        System.exit(0);
    }

}