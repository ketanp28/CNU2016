
class add {
    public static void main(String args[]) {
        add f = new add();
        f.addition(3,4);
    }

    void addition(int a,int b) {
        try {
            System.out.println("sum : "+(a+b));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}