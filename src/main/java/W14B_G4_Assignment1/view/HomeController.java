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


public class HomeController implements Initializable{
    private UIWindow window;

    @FXML
    private Button bthome, btclose, bthistory, btcalculator;

    @FXML
    private Pane pnhistory, pncalculator, pnhome;

    @FXML
    private Label username, fromCur1, fromCur2, fromCur3, fromCur4, toCurNames, cur1List, cur2List, cur3List, cur4List, med, avg, max, min, std;

    @FXML
    private ChoiceBox fromChoice, toChoice, fromChoiceHistory, toChoiceHistory, fromDate, toDate;

    @FXML
    private TextField fromAmount, toAmount;

    @FXML
    private ListView<String> historyList;

    private ObservableList<String> items = FXCollections.observableArrayList ();

    @Override
    public void initialize(URL url, ResourceBundle resources){

    }

    public HomeController(UIWindow window){
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
    public void exit(){
        System.exit(0);
    }

    public void setUsername(String name){
        username.setText(name);
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
        fromChoice.setStyle("-fx-font: 17 arial;");
        toChoice.setStyle("-fx-font: 17 arial;");

        fromChoice.getItems().clear();
        toChoice.getItems().clear();

        for(int i = 0; i < Currency.getCurrencyCount(); i ++){
            fromChoice.getItems().add(Currency.getName(i));
            toChoice.getItems().add(Currency.getName(i));
        }

        fromChoice.setValue(Currency.getName(0));
        toChoice.setValue(Currency.getName(0));
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

    public void updateTable(String filename, String date){
        this.updatePopularTable(date);
        this.updateCalculatePane();
        this.updateHistoryPane(filename);
    }
}