public class PalletItem implements Comparable<PalletItem> {
    private int length;
    private int width;
    private int number;
    private int palletSquare;
    private boolean isPacked;
    private int truckNumber;
    private int price;
    private int count;

    public PalletItem() {
    }

    public PalletItem(int length, int width) {
        this.length = length;
        this.width = width;
        this.palletSquare = length * width;
    }

    public PalletItem(int length, int width, int palletSquare, int price) {
        this.length = length;
        this.width = width;
        this.price = price;
        this.palletSquare = palletSquare;
    }


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPalletSquare() {
        return palletSquare;
    }

    public void setPalletSquare(int palletSquare) {
        this.palletSquare = palletSquare;
    }

    public boolean isPacked() {
        return isPacked;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
    }

    public int getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(int truckNumber) {
        this.truckNumber = truckNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int compareTo(PalletItem o) {
        return o.getLength() - this.getLength();
    }

    @Override
    public String toString() {
        return "PalletItem{" +
            "length=" + length +
            ", width=" + width +
            ", number=" + number +
            ", palletSquare=" + palletSquare +
            ", isPacked=" + isPacked +
            ", truckNumber=" + truckNumber +
            ", price=" + price +
            ", count=" + count +
            '}';
    }
}

