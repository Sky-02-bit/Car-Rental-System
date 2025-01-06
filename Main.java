import java.util.*;

// creating car class which have all the basic details of any car
class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    // creating constructor with same name of the class name
    // this is parameterize type constructor 
    public Car (String carId, String brand,String model,double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    // here we are using encapsulation (Getter and Setter method)

        //      where The meaning of Encapsulation, is to make sure that "sensitive" data is hidden from users. To achieve this, you must:

        // declare class variables/attributes as private
        // provide public get and set methods to access and update the value of a private variable

    public String getCarId(){
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel(){
        return model;
    }

    public double calculatePrice(int rentalDays){
        return (basePricePerDay * rentalDays);
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void rent (){
        isAvailable = false; 
        // if the car is already rented then the avavility of that perticular car will be false
    }

    public void returnCar (){
        isAvailable = true; 
        // if the car is return by the rental person then the avavility of that perticular car will show true that means that car is available.
    }


}

// here we are creating the customer class under which we have also use constructor and encaptulation..
class Customer {
    private String customerId;

    private String name;

    // creating constructor for the customer
    public Customer(String customerId ,String name ) {
        this.customerId = customerId;
        this.name = name;
    }
    // use of getter for geting the customerId

    public String getCustomerId() {
        return customerId;
    }
    
    // use of getter for getting the name of customer
    public String getName() {
        return name;
    }
}

// here we are creating the rental class of the car which will be the combination of both car and customer class
// this show that who(here costumer) is going to take/rent the car 
class Rental {
    private Car car;

    private Customer customer;

    private int days;

    // creating Rental constructor
    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    // encapsulating the things

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

// creating carRentalSystem class
// here we are using ArrayList to store the data instead of database 

class carRentalSystem {
    // crearing constructor
    private List<Car> cars;

    private List<Customer> customers;

    private List<Rental> rentals;

    public carRentalSystem () {
        cars = new ArrayList<>();

        customers = new ArrayList<>();
        
        rentals = new ArrayList<>();
    }

    
    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    // the customer who is willing to rent the car
    public void rentCar(Car car , Customer customer , int days) {
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car, customer, days));
        }
        else {
            System.out.println("The Car you are looking for is not availabe right now ");
        }
    }

    // the customer who is returning the car to the provider
    

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null; //
        for(Rental rental : rentals){
            if(rental.getCar() == car){
                rentalToRemove = rental;
                break;
            }
        }
        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);
            // System.out.println("Car returned successfully.");
        }
        else{
            System.out.println("Car was not rented.");
        }
    }

    //  creating new method with name menu to show a good interface to the terminal
    
        public void menu (){
            Scanner sc = new Scanner(System.in);
            
            while(true) {
                System.out.println("===== Car Rental System =====");
                System.out.println("1. Rent a Car");
                System.out.println("2. Return a Car");
                System.out.println("3. Exit");
                System.out.print("Enter your choice :");
                
                int choice = sc.nextInt();
                sc.nextLine(); // consume new line
                
                if(choice == 1){
                    System.out.println("\n == Rent a Car == \n");
                    System.out.println("Enter Your name : ");
                    String customerName = sc.nextLine();

                    System.out.println("\n Available Cars : ");
                    System.out.println("\n");

                    for(Car car : cars){
                        // checking tha avalability of the car and showing the result
                        if(car.isAvailable()){
                           
                            System.out.println(""+ car.getCarId() + "      " + car.getBrand() +"    "+ car.getModel());
                        }
                    }
                    System.out.println("\n Enter the car ID you want to rent :");
                    String carId = sc.nextLine();

                    System.out.println("\n Enter the number of days for rent :");
                    int rentalDays = sc.nextInt();
                    sc.nextLine(); // consume new line

                    // creating a new customer type object 
                    Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                    addCustomer(newCustomer);

                    Car selectedCar = null ;
                    for(Car car : cars){
                        if(car.getCarId().equals(carId) && car.isAvailable()){
                            selectedCar = car;
                            break;
                        }
                    }
                    if(selectedCar != null){
                        double totalPrice = selectedCar.calculatePrice(rentalDays);
                        System.out.println("\n == Rental Information == \n");
                        System.out.println("Customer ID :" + newCustomer.getCustomerId());
                        System.out.println("Customer Name : " + newCustomer.getName());
                        System.out.println("Car :" + selectedCar.getBrand() + "_" + selectedCar.getModel());
                        System.out.println("Rental Days :" + rentalDays);
                        System.out.printf("Total Price : $%.2f%n" , totalPrice);

                        System.out.println("\n Confirm rental (YES/NO)");
                        String confirm = sc.nextLine();

                        if(confirm.equalsIgnoreCase("YES")){
                            rentCar(selectedCar, newCustomer, rentalDays);
                            System.out.println("\n Car rented successfully !!");
                        }
                        else{
                            System.out.println("\n Rental canceled.");
                        }
                    }
                    else{
                        System.out.println("\n Invalid car selection or not available for rent.");
                    }
                }
                // returning the car
                else if(choice == 2){
                    System.out.println("\n== Return Car ==\n");
                    System.out.println("Enter the car ID you want to return :");
                    String carId = sc.nextLine();

                    Car carToReturn = null;
                    for(Car car : cars){
                        if(car.getCarId().equals(carId) && !car.isAvailable()){
                            carToReturn = car;
                            break;
                        }
                    }
                    if(carToReturn != null){
                        Customer customer = null;
                        for(Rental rental : rentals){
                            if(rental.getCar() == carToReturn){
                                customer = rental.getCustomer();
                            }
                        }
                        if(customer != null){
                            returnCar(carToReturn);
                            System.out.println("Car returned successfully by " + customer.getName());
                        }
                        else{
                            System.out.println("Car was not rented or rental informarion is missing.");
                        }
                    }
                    else{
                        System.out.println("Invalid car ID or car is not rented.");
                    }
                }
                else if(choice == 3){
                    break;
                }
                else{
                    System.out.println("Invalid choice. Please enter a valid option");
                }
            }
            System.out.println("\nThank you for using the Car Rental System :)");
        }
}


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // object 
        carRentalSystem rentalSystem = new carRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Camry", 60.0);

        Car car2 = new Car("C002", "Toyota", "Inova", 260.0);

        Car car3 = new Car("C003", "Mahindra", "Scorpio", 250.0);

        Car car4 = new Car("C004", "Mahindra", "Thar", 180.0);

        Car car5 = new Car("C005", "Honda", "City", 120.0);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);
        rentalSystem.addCar(car5);

        rentalSystem.menu();
    }    
}
