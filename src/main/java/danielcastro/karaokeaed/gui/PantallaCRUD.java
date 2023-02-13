/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package danielcastro.karaokeaed.gui;

import danielcastro.karaokeaed.model.CancionUsuario;
import com.formdev.flatlaf.FlatDarkLaf;
import danielcastro.karaokeaed.dao.CancionDAOImpl;
import danielcastro.karaokeaed.dao.CancionUsuarioDAOImpl;
import danielcastro.karaokeaed.dao.UsuarioDAOImpl;
import danielcastro.karaokeaed.model.Cancion;
import danielcastro.karaokeaed.util.CustomTableHeader;
import danielcastro.karaokeaed.model.Usuario;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Anima
 */
public class PantallaCRUD extends javax.swing.JFrame {
    EntityManager em;
    DefaultTableModel dtm = new DefaultTableModel();
    List<Class> listaClases = new ArrayList();
    List<String> attributeNames = new ArrayList();
    Class ultimaClase;
    LinkedHashMap<Class, List> arrayLists = new LinkedHashMap<>();

    /**
     * Creates new form PantallaPrincipal
     */
    public PantallaCRUD() {
        initComponents();
        this.setLocationRelativeTo(null);
        initEM();
        applyStyles();
        fixedClassesToArrayList();
        createArrayLists();
        comboListener();
    }
    
    private void initEM() {
        this.em = PantallaInicio.em;
    }

    private void applyStyles() {
        // Apply the CustomTableHeader class to the header and the rest of the table
        jTable.getTableHeader().setDefaultRenderer(new CustomTableHeader());
        jTable.setDefaultRenderer(Object.class, new CustomTableHeader());

        // Set background colors
        Color backgroundColor = Color.decode("#dde5b6");
        jPanelMenu.setBackground(backgroundColor);
        jPanelTable.setBackground(backgroundColor);

        // Set the images
        String imagePath = ".\\src\\main\\java\\danielcastro\\karaokeaed\\img\\";
        PantallaInicio.initBGImage(imagePath + "BG4.png"
                , PantallaInicio.labelIntoJPanel(jPanel1));
        PantallaInicio.initBGImage(imagePath + "saveButton.png", botonSave);
        PantallaInicio.initBGImage(imagePath + "cancelBoton.png", botonCancel);
        PantallaInicio.initBGImage(imagePath + "addBoton.png", botonCreate);
        PantallaInicio.initBGImage(imagePath + "deleteBoton.png", botonDelete);
    }


    public Object createInstance(Class clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }
        try {
            Constructor constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException | SecurityException ex) {
            throw new IllegalStateException("Error creating instance of class " + clazz, ex);
        }
    }

    private void fixedClassesToArrayList() {
        LinkedHashMap<String, Class> classMap = new LinkedHashMap<>();
        classMap.put("Cancion", Cancion.class);
        classMap.put("Usuario", Usuario.class);
        classMap.put("CancionUsuario", CancionUsuario.class);
        listaClases.addAll(classMap.values());
        classMap.keySet().forEach(jComboTabla::addItem);
    }

    //Method that creates a list for each class to store the DB info
    private void createArrayLists() {
        //Fills the map with empty ArrayLists
        for (Class clazz : listaClases) {
            List arrayList = new ArrayList();
            arrayLists.put(clazz, arrayList);
        }
        //Populates the ArrayLists with the data from the DB
        for (Class clazz : listaClases) {
            arrayLists.get(clazz).addAll(extractDBData(clazz));
        }
        //Initializes the table to display a class info from the start of the app
        for (Class clazz : listaClases) {
            if (clazz.getSimpleName().equals(jComboTabla.getSelectedItem())) {
                updateTable(clazz, false);
                break;
            }
        }
    }

    //Method that extracts all the info of the database and stores it into its
    //corresponding list.
    private List extractDBData(Class clazz) {
        //Selects everything for each class and stores it in listaClase.
        List<Object> listaClase = viewObject(em, clazz);
        return listaClase;
    }
    
    private List viewObject(EntityManager em, Class clazz) {
        List resultList = new ArrayList();
        if (clazz == Usuario.class) {
            UsuarioDAOImpl usuarioDAOImpl = new UsuarioDAOImpl(em);
            resultList = usuarioDAOImpl.findAll();
        } else if (clazz == Cancion.class) {
            CancionDAOImpl cancionDAOImpl = new CancionDAOImpl(em);
            resultList = cancionDAOImpl.findAll();
        } else if (clazz == CancionUsuario.class) {
            CancionUsuarioDAOImpl cancionUsuarioDAOImpl = new CancionUsuarioDAOImpl(em);
            resultList = cancionUsuarioDAOImpl.findAll();
        }
        return resultList;
    }

    private void updateTable(Class clazz, boolean save) {
        if (save) {
            guardarValores(ultimaClase);
        }
        jTable.setModel(dtm);
        dtm.setRowCount(0);
        dtm.setColumnCount(0);

        attributeNames = getAttributeNames(clazz);
        if (clazz != CancionUsuario.class) {
            attributeNames = attributeNames.subList(0, attributeNames.size() - 1);
        }
        dtm.setColumnIdentifiers(attributeNames.toArray());

        List list = arrayLists.get(clazz);
        List<Map<String, Object>> data;
        data = new ArrayList<>();

        list.forEach(object -> {
            Map<String, Object> attributeValues = getAttributeValues(object);
            Map<String, Object> orderedAttributeValues = new LinkedHashMap<>();
            for (String attributeName : attributeNames) {
                orderedAttributeValues.put(attributeName, attributeValues.get(attributeName));
            }
            data.add(orderedAttributeValues);
        });
        data.forEach(rowData -> {
            dtm.addRow(rowData.values().toArray());
        });
        ultimaClase = clazz;
    }

    private List<String> getAttributeNames(Class clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        List<String> attributeNames = new ArrayList<>(declaredFields.length);

        for (Field field : declaredFields) {
            attributeNames.add(field.getName());
        }
        return attributeNames;
    }

    private Map<String, Object> getAttributeValues(Object object) {
        Map<String, Object> attributeValues = new HashMap<>();
        Arrays.stream(object.getClass().getDeclaredFields())
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        attributeValues.put(field.getName(), field.get(object));
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(PantallaCRUD.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        field.setAccessible(false);
                    }
                });
        return attributeValues;
    }

    //Method that updates the table when the comboBox value that has the 
    //database tables is changed
    private void comboListener() {
        jComboTabla.addActionListener(new ActionListener() {
            //Gets the selected item and compares it to the classes in 'listaClases'
            //until it finds the right one
            public void actionPerformed(ActionEvent e) {
                Object selectedItem = jComboTabla.getSelectedItem();
                for (Class clazz : listaClases) {
                    if (clazz.getSimpleName().equals(selectedItem)) {
                        updateTable(clazz, true);
                        break;
                    }
                }
            }
        });
    }

    //Method that checks if we are changing the current table selected 
    //on the comboBox and saves the changes made to that table on its
    //corresponding list
    private void guardarValores(Class clazz) {
        List arrayList = arrayLists.get(clazz);
        arrayList.clear();
        int rowCount = jTable.getRowCount();
        int columnCount = jTable.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            Object instance = createInstance(clazz);
            for (int j = 0; j < columnCount; j++) {
                String columnName = jTable.getColumnName(j);
                Object value = jTable.getValueAt(i, j);
                value = isFechaNull(value);
                setValueForInstance(clazz, instance, columnName, value);
            }
            arrayList.add(instance);
        }
    }

    private void setValueForInstance(Class clazz, Object instance, String fieldName, Object value) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Class fieldType = field.getType();
            switch (fieldType.getName()) {
                case "int" ->
                    value = Integer.valueOf(value.toString());
                case "boolean" ->
                    value = Boolean.valueOf(value.toString());
                case "java.lang.String" ->
                    value = value.toString();
                default -> {
                    if (fieldType == Cancion.class || fieldType == Usuario.class) {
                        value = getObjectValue(fieldType, value);
                    }
                }
            }
            field.set(instance, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(PantallaCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Object getObjectValue(Class fieldType, Object value) {
        if (value instanceof String) {
        String string = (String) value;
        int id = Integer.parseInt(string);
            Object objeto = em.find(fieldType, id);
            return objeto;
        } else {
            return value;
        }
    }
    
    private Object isFechaNull(Object fechaObjeto) {
        LocalDate fecha = null;
        if(fechaObjeto == null) {
            fecha = LocalDate.now();
            return fecha;
        }
        return fechaObjeto;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanelMenu = new javax.swing.JPanel();
        botonDelete = new javax.swing.JButton();
        botonCreate = new javax.swing.JButton();
        botonCancel = new javax.swing.JButton();
        botonSave = new javax.swing.JButton();
        jComboTabla = new javax.swing.JComboBox<>();
        jPanelTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelMenu.setOpaque(false);

        botonDelete.setBorderPainted(false);
        botonDelete.setContentAreaFilled(false);
        botonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDeleteActionPerformed(evt);
            }
        });

        botonCreate.setBorderPainted(false);
        botonCreate.setContentAreaFilled(false);
        botonCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCreateActionPerformed(evt);
            }
        });

        botonCancel.setBorderPainted(false);
        botonCancel.setContentAreaFilled(false);
        botonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelActionPerformed(evt);
            }
        });

        botonSave.setBorderPainted(false);
        botonSave.setContentAreaFilled(false);
        botonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelMenuLayout = new javax.swing.GroupLayout(jPanelMenu);
        jPanelMenu.setLayout(jPanelMenuLayout);
        jPanelMenuLayout.setHorizontalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(botonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119)
                .addComponent(jComboTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 303, Short.MAX_VALUE)
                .addComponent(botonCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(botonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(botonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanelMenuLayout.setVerticalGroup(
            jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuLayout.createSequentialGroup()
                .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(botonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(botonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(botonDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                                    .addComponent(botonCreate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanelMenuLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jComboTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelTable.setOpaque(false);

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable);

        javax.swing.GroupLayout jPanelTableLayout = new javax.swing.GroupLayout(jPanelTable);
        jPanelTable.setLayout(jPanelTableLayout);
        jPanelTableLayout.setHorizontalGroup(
            jPanelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTableLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 934, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanelTableLayout.setVerticalGroup(
            jPanelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 563, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 100, Short.MAX_VALUE)
                    .addComponent(jPanelTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelActionPerformed
        // TODO add your handling code here:
        PantallaInicio pli = new PantallaInicio();
        this.dispose();
        pli.setVisible(true);
    }//GEN-LAST:event_botonCancelActionPerformed

    private void botonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSaveActionPerformed
        guardarValores(ultimaClase);

        List<Object> listaToDeleteCancion = new ArrayList<>();
        List<Object> listaToDeleteUsuario = new ArrayList<>();
        List<Object> listaToDeleteCancionUsuario = new ArrayList<>();
        
        List<Map.Entry<Class, List>> lista = new ArrayList(arrayLists.entrySet());
        for (Map.Entry<Class, List> entry : lista) {
            Class key = entry.getKey();
            List listaActual = entry.getValue();
            List listaClaseDB = extractDBData(key);
            Map<Object, Object> mapDB = (Map<Object, Object>) listaClaseDB.stream().collect(Collectors.toMap(
                obj -> em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(obj), obj -> obj));

            List<Object> listaToCreate = new ArrayList<>();
            List<Object> listaToUpdate = new ArrayList<>();
            
            // Create
            for (Object objetoApp : listaActual) {
                Object idObjetoApp = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(objetoApp);
                if (!mapDB.containsKey(idObjetoApp)) {
                    listaToCreate.add(objetoApp);
                }
            }

            // Loop to handle the update operation
            for (Object objetoApp : listaActual) {
                Object idObjetoApp = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(objetoApp);
                if (mapDB.containsKey(idObjetoApp)) {
                    listaToUpdate.add(objetoApp);
                }
            }

            // Loop to handle the delete operation
            for (Object objectDB : listaClaseDB) {
                Object idObjectDB = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(objectDB);
                if (!listaActual.stream().anyMatch(obj -> em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(obj).equals(idObjectDB))) {
                    if(key.getSimpleName().equals("Cancion")) {
                        listaToDeleteCancion.add(objectDB);
                    }
                    if(key.getSimpleName().equals("Usuario")) {
                        listaToDeleteUsuario.add(objectDB);
                    }
                    if(key.getSimpleName().equals("CancionUsuario")) {
                        listaToDeleteCancionUsuario.add(objectDB);
                    }
                }
            }
            if(key.getSimpleName().equals("Cancionusuario"))
                deleteObjects(em, key, listaToDeleteCancionUsuario);
            createObjects(em, key, listaToCreate);
            updateObjects(em, key, listaToUpdate);
        }
        deleteObjects(em, Cancion.class, listaToDeleteCancion);
        deleteObjects(em, Usuario.class, listaToDeleteUsuario);
        PantallaCRUD screenCRUD = new PantallaCRUD();
        this.dispose();
        screenCRUD.setVisible(true);
    }//GEN-LAST:event_botonSaveActionPerformed
 
    private void createObjects(EntityManager em, Class clazz, List<Object> list) {
        if (clazz == Usuario.class) {
            UsuarioDAOImpl usuarioDAOImpl = new UsuarioDAOImpl(em);
            for (Object object : list) {
                usuarioDAOImpl.add((Usuario) object);
            }
        } else if (clazz == Cancion.class) {
            CancionDAOImpl cancionDAOImpl = new CancionDAOImpl(em);
            for (Object object : list) {
                cancionDAOImpl.add((Cancion) object);
            }
        } else if (clazz == CancionUsuario.class) {
            CancionUsuarioDAOImpl cancionUsuarioDAOImpl = new CancionUsuarioDAOImpl(em);
            for (Object object : list) {
                cancionUsuarioDAOImpl.add((CancionUsuario) object);
            }
        }
    }
    
    private void updateObjects(EntityManager em, Class clazz, List<Object> list) {
        if (clazz == Usuario.class) {
            UsuarioDAOImpl usuarioDAOImpl = new UsuarioDAOImpl(em);
            for (Object object : list) {
                usuarioDAOImpl.update((Usuario) object);
            }
        } else if (clazz == Cancion.class) {
            CancionDAOImpl cancionDAOImpl = new CancionDAOImpl(em);
            for (Object object : list) {
                cancionDAOImpl.update((Cancion) object);
            }
        } else if (clazz == CancionUsuario.class) {
            CancionUsuarioDAOImpl cancionUsuarioDAOImpl = new CancionUsuarioDAOImpl(em);
            for (Object object : list) {
                cancionUsuarioDAOImpl.update((CancionUsuario) object);
            }
        }
    }
        
    private void deleteObjects(EntityManager em, Class clazz, List<Object> list) {
        if (clazz == Usuario.class) {
            UsuarioDAOImpl usuarioDAOImpl = new UsuarioDAOImpl(em);
            for (Object object : list) {
                usuarioDAOImpl.delete((Usuario) object);
            }
        } else if (clazz == Cancion.class) {
            CancionDAOImpl cancionDAOImpl = new CancionDAOImpl(em);
            for (Object object : list) {
                cancionDAOImpl.delete((Cancion) object);
            }
        } else if (clazz == CancionUsuario.class) {
            CancionUsuarioDAOImpl cancionUsuarioDAOImpl = new CancionUsuarioDAOImpl(em);
            for (Object object : list) {
                cancionUsuarioDAOImpl.delete((CancionUsuario) object);
            }
        }
    }

    
    private void botonCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCreateActionPerformed
        // TODO add your handling code here:
        //Comprueba la clase seleccionada en el comboBox y la guarda
        Object selectedItem = jComboTabla.getSelectedItem();

        for (Class clazz : listaClases) {
            if (clazz.getSimpleName().equals(selectedItem)) {
                try {
                    Object emptyRow = createInstance(clazz);
                    Field[] fields = clazz.getDeclaredFields();
                    Object[] rowData = new Object[fields.length];
                    for (int i = 0; i < fields.length; i++) {
                        fields[i].setAccessible(true);
                        rowData[i] = fields[i].get(emptyRow);
                    }
                    dtm.addRow(rowData);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_botonCreateActionPerformed

    private void botonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDeleteActionPerformed
        // TODO add your handling code here:
        int filaSeleccion = jTable.getSelectedRow();
        if (filaSeleccion != -1) {
            dtm.removeRow(filaSeleccion);
        } else {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "No hay ninguna fila seleccionada."
                    + " Seleccione una fila para eliminarla.",
                    "Error de selección", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_botonDeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        FlatDarkLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new PantallaCRUD().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCancel;
    private javax.swing.JButton botonCreate;
    private javax.swing.JButton botonDelete;
    private javax.swing.JButton botonSave;
    private javax.swing.JComboBox<String> jComboTabla;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelMenu;
    private javax.swing.JPanel jPanelTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables
}
