package vending;

public class VendingMachineFactory {
    public VendingMachineFactory() {
        //
    }

    public static VendingMachine createVendingMachine() {
        return new VendingMachineImpl();
    }
}
