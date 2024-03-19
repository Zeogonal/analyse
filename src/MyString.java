public class MyString {
    String text;
    private int maxAmount;

    public MyString() {
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return
                "maxAmount = " + maxAmount +
                        ", text = " + text;
    }
}// class