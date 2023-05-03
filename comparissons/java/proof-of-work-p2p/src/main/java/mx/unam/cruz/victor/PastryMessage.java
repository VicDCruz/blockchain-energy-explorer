package mx.unam.cruz.victor;


import lombok.AllArgsConstructor;
import lombok.Getter;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;

@AllArgsConstructor
public class PastryMessage implements Message {
    private final Id from;
    private final Id to;
    @Getter
    private final String messageBody;

    @Override
    public int getPriority() {
        return Message.LOW_PRIORITY;
    }

    public String toString() {
        return "PastryMessage(from=" + this.from.toStringFull() +
               ", to=" + this.to.toStringFull() +
               ", messageBody=" + this.messageBody + ")";
    }
}
