package mx.unam.cruz.victor.notes;

import com.opencsv.bean.CsvBindByPosition;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(force = true)
public final class RawAccount {
    @CsvBindByPosition(position = 1)
    private final String address;
    @CsvBindByPosition(position = 2)
    private final String mnemonic;

    public RawAccount(String address,
                      String mnemonic) {
        this.address = address;
        this.mnemonic = mnemonic;
    }

    public String address() {
        return address;
    }

    public String mnemonic() {
        return mnemonic;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RawAccount) obj;
        return Objects.equals(this.address, that.address) &&
               Objects.equals(this.mnemonic, that.mnemonic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, mnemonic);
    }

    @Override
    public String toString() {
        return "RawAccount[" +
               "address=" + address + ", " +
               "mnemonic=" + mnemonic + ']';
    }

}
