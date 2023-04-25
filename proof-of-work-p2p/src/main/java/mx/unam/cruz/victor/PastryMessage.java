package mx.unam.cruz.victor;


import lombok.AllArgsConstructor;
import lombok.ToString;
import rice.p2p.commonapi.Id;
import rice.p2p.commonapi.Message;

@AllArgsConstructor
@ToString
public class PastryMessage implements Message {
    private final Id from;
    private final Id to;
    private final String messageBody;

    @Override
    public int getPriority() {
        return Message.LOW_PRIORITY;
    }
}
