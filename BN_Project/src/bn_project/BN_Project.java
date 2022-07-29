/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bn_project;

import com.brunomnsilva.smartgraph.graph.*;
import com.brunomnsilva.smartgraph.graphview.*;
import java.util.*;
import java.util.concurrent.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

/**
 *
 * @author Goldie Srulovich
 */
public class BN_Project extends Application {

    //Digraph<String, String> g = new DigraphEdgeList<>();
    //SmartPlacementStrategy strategy;/// = new SmartCircularSortedPlacementStrategy();
    //SmartGraphPanel<String, String> graphView;// = new SmartGraphPanel<>(g, strategy);
    //BorderPane border;// = new BorderPane();
    final char delimiterChar = ','; // this is the delimiter character which seperates a RV name from another RV name - used for naming edges

    public void removeEdges(Graph<String, String> g, SmartGraphPanel<String, String> graphView, ArrayList<Edge<String, String>> listEdges) {

        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (Exception e) {
        }
        for (int i = 0; i < listEdges.size(); i++) {
            Edge<String, String> edge = listEdges.get(i);
            g.removeEdge(edge);
        }
        graphView.update();
    }

    public ArrayList<Edge<String, String>> addNodes(Graph<String, String> g, SmartGraphPanel<String, String> graphView, int amount) {
        /*
        Thread thread = new Thread() {
            public void run() {
                //System.out.println("Thread Running");
                int max_amount = amount;
                for (int i = 1; i <= max_amount; i++) {
                    try {
                        g.insertVertex(i + "");
                        //TimeUnit.SECONDS.sleep(1);
                        //graphView.update();
                    } catch (Exception e) {
                        max_amount++;
                    }
                    //graphView.update();
                    //TimeUnit.SECONDS.sleep(1);
                }

                graphView = new SmartGraphPanel<>(g, strategy);
                border.setCenter(graphView);
                graphView.init();
            }
        };

        thread.start();
         */

        ArrayList<Edge<String, String>> listEdges = new ArrayList<>();

        int max_amount = amount;
        for (int i = 1; i <= max_amount; i++) {
            try {
                g.insertVertex(i + "");

                Edge<String, String> edge = null;
                if (i > 1) {
                    //Edge<String, String> e  = (i - 1 + "", i + "", i - 1 + "" + i + "");

                    edge = g.insertEdge(i - 1 + "", i + "", i - 1 + "" + i + "");
                    listEdges.add(edge);
                }
                graphView.update();
                //TimeUnit.SECONDS.sleep(1);
                //graphView.update();
            } catch (Exception e) {
                max_amount++;
            }

            //g.r //TimeUnit.SECONDS.sleep(1);
        }

        //for (int i = 0; i < listEdges.size(); i++) {
        //    Edge<String, String> edge = listEdges.get(i);
        //    g.removeEdge(edge);
        //}
        //graphView = new SmartGraphPanel<>(g, new SmartCircularSortedPlacementStrategy());
        //border.setCenter(graphView);
        //graphView.init();
        return listEdges;
    }

    @Override
    public void start(Stage primaryStage) {
        /*
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
         */

        //create the graph
        //Graph<String, String> g = new GraphEdgeList<>();
        //... see example below
        Digraph<String, String> g = new DigraphEdgeList<>();
        /*
        g.insertVertex("A");
        g.insertVertex("B");
        g.insertVertex("C");
        g.insertVertex("D");
        g.insertVertex("E");
        g.insertVertex("F");

        g.insertEdge("A", "B", "A" + delimiterChar + "B");
        g.insertEdge("B", "A", "B" + delimiterChar + "A");
        g.insertEdge("A", "C", "A" + delimiterChar + "C");
        g.insertEdge("A", "D", "A" + delimiterChar + "D");
        g.insertEdge("B", "C", "B" + delimiterChar + "C");
        g.insertEdge("C", "D", "C" + delimiterChar + "D");
        g.insertEdge("B", "E", "B" + delimiterChar + "E");
        g.insertEdge("F", "D", "F" + delimiterChar + "D");
        g.insertEdge("F", "D", "D" + delimiterChar + "F2");

        //for (Vertex<String> v : g.vertices()) {
        //    if (v.element().equals("B")) {
        //        g.removeVertex(v);
        //g.rep
        //        System.out.println("Found B!!!!!!!");
        //    }
        // }
        //yep, its a loop!
        g.insertEdge("A", "A", "A" + delimiterChar + "A");
         */
        SmartRandomPlacementStrategy strategy = new SmartRandomPlacementStrategy();//new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, strategy);
        graphView.setAutomaticLayout(true);
        graphView.setVertexDoubleClickAction(graphVertex -> {
            System.out.println("Vertex contains element: " + graphVertex.getUnderlyingVertex().element());

            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            VBox dialogVbox = new VBox(10);
            Text updateNodeText = new Text("Update Node " + graphVertex.getUnderlyingVertex().element() + ":");
            updateNodeText.setFont(Font.font("Verdana", 20));
            updateNodeText.setFill(Color.DARKGREY);

            Button deleteNodeBtn = new Button("Delete node " + graphVertex.getUnderlyingVertex().element());

            dialogVbox.getChildren().add(new Separator());
            //dialogVbox.getChildren().add(new Text("Delete?"));
            dialogVbox.getChildren().add(deleteNodeBtn);
            dialogVbox.getChildren().add(new Separator());
            dialogVbox.getChildren().add(updateNodeText);
            dialogVbox.getChildren().add(new Text("Node name:"));

            TextField nodeNameField = new TextField(graphVertex.getUnderlyingVertex().element());
            Button updateBtn = new Button("Update");

            dialogVbox.getChildren().add(nodeNameField);
            dialogVbox.getChildren().add(new Text("\nSelect set of parents:"));

            ArrayList<CheckBox> allParentChackboxes = new ArrayList<>();

            for (Vertex<String> v : g.vertices()) {
                if (v.element().equals(graphVertex.getUnderlyingVertex().element())) {
                    continue;
                }
                CheckBox parent = new CheckBox(v.element());
                allParentChackboxes.add(parent);
                for (Edge<String, String> e : g.edges()) {

                    if (e.element().equals(v.element() + delimiterChar + graphVertex.getUnderlyingVertex().element())) {
                        parent.setSelected(true);
                    }
                }
                dialogVbox.getChildren().add(parent);
            }
            if (g.vertices().size() == 1) {
                dialogVbox.getChildren().add(new Text("(none available)"));
            }

            dialogVbox.getChildren().add(updateBtn);

            updateBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //... do something in here.
                    System.out.println("Clicked 'Update' button!");

                    dialog.close();
                    /*
                        int amount = Integer.valueOf(numNodesField.getText());
                        ArrayList<Edge<String, String>> listEdges = addNodes(g, graphView, amount);
                        graphView.update();

                        Thread thread = new Thread() {
                            public void run() {
                                //System.out.println("Thread Running");
                                removeEdges(g, graphView, listEdges);
                            }
                        };

                        thread.start();
                     */

                    ArrayList<String> childrenOfNode = new ArrayList<>();

                    for (Edge<String, String> e : g.edges()) {

                        if (e.element().startsWith(graphVertex.getUnderlyingVertex().element() + delimiterChar)) {
                            childrenOfNode.add(e.element().split("" + delimiterChar)[1]);
                            //childrenOfNode.add();
                        }
                    }

                    for (Vertex<String> v : g.vertices()) {
                        if (v.element().equals(graphVertex.getUnderlyingVertex().element())) {
                            g.removeVertex(v);
                            //g.rep
                            System.out.println("Found " + graphVertex.getUnderlyingVertex().element() + "!!!!!!!");
                        }
                    }

                    //nodeNameField.getText();
                    g.insertVertex(nodeNameField.getText());

                    for (String child : childrenOfNode) {
                        g.insertEdge(nodeNameField.getText(), child, nodeNameField.getText() + delimiterChar + child);
                    }

                    for (CheckBox c : allParentChackboxes) {

                        if (c.isSelected()) {

                            String parent = c.getText();

                            g.insertEdge(parent, nodeNameField.getText(), parent + delimiterChar + nodeNameField.getText());

                        }
                    }

                    graphView.update();

                }
            });

            deleteNodeBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //... do something in here.
                    System.out.println("Clicked 'Delete Node' button!");

                    dialog.close();
                    /*
                        int amount = Integer.valueOf(numNodesField.getText());
                        ArrayList<Edge<String, String>> listEdges = addNodes(g, graphView, amount);
                        graphView.update();

                        Thread thread = new Thread() {
                            public void run() {
                                //System.out.println("Thread Running");
                                removeEdges(g, graphView, listEdges);
                            }
                        };

                        thread.start();
                     */

                    //ArrayList<String> childrenOfNode = new ArrayList<>();
                    for (Edge<String, String> e : g.edges()) {

                        if (e.element().startsWith(graphVertex.getUnderlyingVertex().element() + delimiterChar)) {
                            //childrenOfNode.add(e.element().split("" + delimiterChar)[1]);
                            //childrenOfNode.add();
                            g.removeEdge(e);
                        }
                        if (e.element().endsWith(delimiterChar + graphVertex.getUnderlyingVertex().element())) {
                            g.removeEdge(e);
                        }
                    }

                    for (Vertex<String> v : g.vertices()) {
                        if (v.element().equals(graphVertex.getUnderlyingVertex().element())) {
                            g.removeVertex(v);
                            //g.rep
                            System.out.println("Found " + graphVertex.getUnderlyingVertex().element() + "!!!!!!!");
                        }
                    }

                    //nodeNameField.getText();
                    //g.insertVertex(nodeNameField.getText());
                    //for (String child : childrenOfNode) {
                    //    g.insertEdge(nodeNameField.getText(), child, nodeNameField.getText() + delimiterChar + child);
                    //}
                    // for (CheckBox c : allParentChackboxes) {
                    //     if (c.isSelected()) {
                    //        String parent = c.getText();
                    //        g.insertEdge(parent, nodeNameField.getText(), parent + delimiterChar + nodeNameField.getText());
                    //    }
                    //}
                    graphView.update();

                }
            });

            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setPrefSize(602, 455);

            //AnchorPane anchorPane = new AnchorPane();
            //anchorPane.setPrefSize(600, 400);
            scrollPane.setContent(dialogVbox);

            Scene dialogScene = new Scene(scrollPane, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();

        });

        //Scene scene = new Scene(graphView, 1024, 768);
        Button button = new Button("Add Nodes");

        Font font = Font.font("Courier New", FontWeight.BOLD, 20);

        button.setFont(font);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //... do something in here.
                System.out.println("Clicked 'Add Nodes' button!");

                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(primaryStage);
                VBox dialogVbox = new VBox(20);
                dialogVbox.getChildren().add(new Text("Please enter number of nodes to add \nto the network below:"));
                TextField numNodesField = new TextField("");
                Button addBtn = new Button("Add");
                dialogVbox.getChildren().add(numNodesField);
                dialogVbox.getChildren().add(addBtn);

                addBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        //... do something in here.
                        System.out.println("Clicked 'Add' button!");

                        dialog.close();
                        int amount = Integer.valueOf(numNodesField.getText());
                        ArrayList<Edge<String, String>> listEdges = addNodes(g, graphView, amount);
                        graphView.update();

                        Thread thread = new Thread() {
                            public void run() {
                                //System.out.println("Thread Running");
                                removeEdges(g, graphView, listEdges);
                            }
                        };

                        thread.start();

                    }
                });

                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        });

        HBox hbox = new HBox(8); // spacing = 8
        hbox.getChildren().addAll(button);

        BorderPane border = new BorderPane();
        border.setTop(hbox);
        border.setLeft(new VBox(8));
        border.setCenter(graphView);
        border.setRight(new VBox(8));

        Scene scene = new Scene(border, 700, 500);

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setMaximized(true);
        stage.setTitle("Bayesian Network Visualization");
        stage.setScene(scene);
        stage.show();

        //IMPORTANT - Called after scene is displayed so we can have width and height values
        graphView.init();

        //graphView.update();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
