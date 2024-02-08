import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class AddressBook {
private Map<String, String> contacts;
private String Archivo;
public AddressBook(String Archivo) {
    this.Archivo = Archivo;
    this.contacts = new HashMap<>();
}
public void load() {
    try (BufferedReader reader = new BufferedReader(new FileReader(Archivo))) {
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("Linea del archivo: " + line);
            String[] parts = line.split(",");
            contacts.put(parts[0], parts[1]);
        }
        System.out.println("Contactos guardados");
    }
    catch (IOException e) {
        System.err.println("Error" + e.getMessage());
    }
}
public void save() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(Archivo))) {
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            writer.write(entry.getKey() + " , " + entry.getValue());
            writer.newLine();
        }
        System.out.println("Contacto guardado");
    } catch (IOException e) {
        System.err.println("Error " + e.getMessage());
    }
}
public void list() {
    System.out.println("Contactos:");
    for (Map.Entry<String, String> entry : contacts.entrySet()) {
        System.out.println(entry.getKey() + " : " + entry.getValue());
    }
}
public void create(String phoneNumber, String name) {
    contacts.put(phoneNumber, name);
    System.out.println("Contacto creado: " + phoneNumber + ", " + name);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(Archivo, true))) {
        writer.write(phoneNumber + "," + name);
        writer.newLine();
        System.out.println("Nuevo contacto guardado");
    } catch (IOException e) {
        System.err.println("Error al guardar " + e.getMessage());

    }
}
public void delete(String phoneNumber) {
    if (contacts.containsKey(phoneNumber)) {
        contacts.remove(phoneNumber);
        System.out.println("Contacto eliminado: " + phoneNumber);
    } else {
        System.out.println("Este contacto no existe  " + phoneNumber);
    }
}
public static void main(String[] args) {
    AddressBook addressBook = new AddressBook("contacts.cvs");
    addressBook.load();
    Scanner scanner =  new Scanner(System.in);

    int choice;
    do {
        System.out.println("\nMenú:");
        System.out.println("1. Listar contactos");
        System.out.println("2. Crear contacto");
        System.out.println("3. Eliminar contacto");
        System.out.println("4. Guardar y salir");
        System.out.print("Seleccione una opción: ");
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                addressBook.list();
                break;
            case 2:
                System.out.print("Ingrese el número telefónico: ");
                String phoneNumber = scanner.nextLine();
                System.out.print("Ingrese el nombre: ");
                String name = scanner.nextLine();
                addressBook.create(phoneNumber, name);
                break;
            case 3:
                System.out.print("Ingrese el número telefónico del contacto a eliminar: ");
                String numberToDelete = scanner.nextLine();
                addressBook.delete(numberToDelete);
                break;
            case 4:
                addressBook.save();
                break;
            default:
                System.out.println("Opcion incorrecta");
        }
    } while (choice != 4);
    scanner.close();
}
}