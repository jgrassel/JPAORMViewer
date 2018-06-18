/*******************************************************************************
 * Copyright (c) 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package com.ibm.ws.jpaormscanner.tools.was_reader;


import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class WASReaderApplication extends Application  {
    private TabPane tabPane = new TabPane();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("WebSphere JPA Application Viewer");
        
        Scene scene = new Scene(new VBox(), 1000, 650);
        
        setupMenu(primaryStage, scene);
        
        ((VBox) scene.getRoot()).getChildren().addAll(tabPane); // borderPane);
        VBox.setVgrow(tabPane, javafx.scene.layout.Priority.ALWAYS);
        

        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
    
    /**
     * Set up the Menu Bar
     * 
     * @param scene
     */
    private void setupMenu(Stage stage, Scene scene) {
        MenuBar menuBar = new MenuBar();
        
        // File Menu
        Menu menuFile = new Menu("File");
        
        MenuItem open = new MenuItem("Open...");
        open.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open WebSphere JPA ORM Diagnostic File");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
                File f = fileChooser.showOpenDialog(stage);
                openFile(f);
            } });
        menuFile.getItems().add(open);
        
        MenuItem pasteIn = new MenuItem("Paste In...");
        pasteIn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(stage);
                
                VBox dialogVbox = new VBox(20);
                dialogVbox.getChildren().add(new Text("Paste in the JPA ORM Diagnostic XML data below:"));

                TextArea pasteArea = new TextArea();
                pasteArea.setEditable(true);
                dialogVbox.getChildren().add(pasteArea);
                VBox.setVgrow(pasteArea, javafx.scene.layout.Priority.ALWAYS);
                
                HBox buttonHBox = new HBox();
                
                javafx.scene.control.Button submitButton = new javafx.scene.control.Button("Submit");
                javafx.scene.control.Button cancelButton = new javafx.scene.control.Button("Cancel");
                
                javafx.scene.layout.Region lRegion = new javafx.scene.layout.Region();
                javafx.scene.layout.Region mRegion = new javafx.scene.layout.Region();
                javafx.scene.layout.Region rRegion = new javafx.scene.layout.Region();
                HBox.setHgrow(lRegion, javafx.scene.layout.Priority.ALWAYS);
                HBox.setHgrow(mRegion, javafx.scene.layout.Priority.ALWAYS);
                HBox.setHgrow(rRegion, javafx.scene.layout.Priority.ALWAYS);
                
                buttonHBox.getChildren().addAll(lRegion, submitButton, mRegion, cancelButton, rRegion);                
                dialogVbox.getChildren().add(buttonHBox); 
                
                submitButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, 
                        new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override public void handle(javafx.scene.input.MouseEvent e) {
                        String text = pasteArea.getText();
                        if (text == null || text.isEmpty()) {
                            return;
                        }
                        
                        readPasteData(text);
                        dialog.close();
                    }
                });
                cancelButton.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, 
                        new EventHandler<javafx.scene.input.MouseEvent>() {
                    @Override public void handle(javafx.scene.input.MouseEvent e) {
                        dialog.close();
                    }
                });
                
                Scene dialogScene = new Scene(dialogVbox, 600, 400);
                dialog.setScene(dialogScene);
                dialog.show();
            } });
        menuFile.getItems().add(pasteIn);
        
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.exit(0);              
            } });
        menuFile.getItems().add(exit);
        
        // Edit Menu
        Menu menuEdit = new Menu("Edit");
        
        MenuItem copy = new MenuItem("Copy");
        menuEdit.getItems().add(copy);       
        
        // Finalize the Menu
        menuBar.getMenus().addAll(menuFile, menuEdit);
        ((VBox) scene.getRoot()).getChildren().addAll(menuBar);
    }
    
    private void readPasteData(String pasteData) {
        if (pasteData == null) {
            return;
        }
        
        try {
            ORMLogData ormLogData = ORMLogData.createORMLogData(pasteData);           
            createTab(ormLogData);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error opening JPA Data File");
            alert.setContentText(e.toString());           
            alert.showAndWait();
        }     
    }
    private void openFile(File f) {
        if (f == null) {
            return;
        }
        
        // Do not open any log file that is already open.
        ObservableList<Tab> tabsList = tabPane.getTabs();
        for (Tab t : tabsList) {
            ORMLogData ormLogData = (ORMLogData) t.getUserData();
            if (ormLogData.getFile() != null && f.equals(ormLogData.getFile())) {
                return;
            }
        }
        
        try {
            ORMLogData ormLogData = ORMLogData.createORMLogData(f);           
            createTab(ormLogData);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error opening JPA Data File");
            alert.setContentText(e.toString());           
            alert.showAndWait();
        }       
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void createTab(ORMLogData ormLogData) throws Exception {
        Tab tab = new Tab();
        String puName = ormLogData.getPersistenceUnitName();
        tab.setText(puName);
        tab.setUserData(ormLogData);
        
        tabPane.getTabs().add(tab);
        
        tab.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                // Do nothing for now
            }
        });
        
        SplitPane upDownSplitPane = new SplitPane();
        tab.setContent(upDownSplitPane);
        
        SplitPane splitPane = new SplitPane();
            
        // Set up Info Display on Center
        TextArea infoDisplayTextArea = new TextArea();
        infoDisplayTextArea.setEditable(false);
        
        // Set up Tree View on Left
        TreeItem<String> rootItem = new TreeItem<String>(puName);
        rootItem.setExpanded(true);
        
        TreeItem<String> classesItem = new TreeItem<String>("Classes");
        rootItem.getChildren().add(classesItem);
        
        List<TreeItem<String>> classTreeList = ormLogData.getScannedClassesTreeItems();
        for (TreeItem<String> item : classTreeList) {
            classesItem.getChildren().add(item);
        }    
        
        TreeItem<String> ormFilesItem = new TreeItem<String>("Entity Mappings");
        rootItem.getChildren().add(ormFilesItem); 
        
        List<TreeItem<String>> entityMappingsTreeList = ormLogData.getEntityMappingsTreeItems();
        for (TreeItem<String> item : entityMappingsTreeList) {
            ormFilesItem.getChildren().add(item);
        }  
        
        TreeView treeView = new TreeView(rootItem);
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {

            @Override
            public void changed(
                    ObservableValue<? extends TreeItem<String>> observable,
                    TreeItem<String> old_val, TreeItem<String> new_val) {
                if (new_val == rootItem) {
                    infoDisplayTextArea.setText(ormLogData.getPersistenceXml());
                } else if (new_val == classesItem || new_val == ormFilesItem) {
                    //
                } else {
                    infoDisplayTextArea.setText(ormLogData.getTabData(new_val));
                }
            }

        });
        
        MultipleSelectionModel msm = treeView.getSelectionModel();
        int row = treeView.getRow(rootItem);
        msm.select(row);

        splitPane.getItems().addAll(treeView, infoDisplayTextArea);
        splitPane.setDividerPosition(0, 0.2f);
        
        // Set up properties on bottom
        TableView propsTable = new TableView();
        propsTable.setEditable(false);
        
        TableColumn propNameCol = new TableColumn("Property Name");
        propNameCol.setMinWidth(200f);
        propNameCol.setCellValueFactory(
                new PropertyValueFactory<ORMLogData.ORMProperty, String>("name"));
        TableColumn propValCol = new TableColumn("Value");
        propValCol.setMinWidth(400f);
        propValCol.setCellValueFactory(
                new PropertyValueFactory<ORMLogData.ORMProperty, String>("value"));
        propsTable.getColumns().clear();
        propsTable.getColumns().addAll(propNameCol, propValCol);
        propsTable.setItems(ormLogData.getPropertiesDataSource());
        
//        ListView<String> propsListView = ormLogData.getPropertiesListView();
        
        upDownSplitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        upDownSplitPane.getItems().addAll(splitPane, propsTable);
        upDownSplitPane.setDividerPosition(0, 0.8f);
    }
}
