package assistant;

import org.hibernate.collection.internal.PersistentBag;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-28
 * Time: 10:02
 */
public abstract class Serializer {
    /**
     * Used to deSerialize an object
     * @param classType
     * @param serializedObject
     * @param <T>               Returns the object as the given class type.
     * @return
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     */
    //TODO Check if classType is not needed to be used in some way
    public static final <T>  T deSerialize(java.lang.Class<T> classType, String serializedObject) throws IOException,
            ClassNotFoundException {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte [] data = base64Decoder.decodeBuffer(serializedObject);
        HackedObjectInputStream ois = new HackedObjectInputStream(
                new ByteArrayInputStream( data ) );

        Object o = ois.readObject();

        ois.close();

        return (T)o;
    }

    private static class HackedObjectInputStream extends ObjectInputStream {

        public HackedObjectInputStream(InputStream in) throws IOException {
            super(in);
        }




        @Override
        protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
            ObjectStreamClass resultClassDescriptor = super.readClassDescriptor();
//            String className = resultClassDescriptor.getName();
//            if (className.contains("models.Reply")){
//                Class modelsSuperClass = Class.forName(className).getSuperclass();
//                resultClassDescriptor = ObjectStreamClass.lookup(modelsSuperClass);
//            }

            return resultClassDescriptor;
        }
    }

    /**
     * Used to serialize an object. Do not forget that the object and its sub-objects must implement serializable.
     * @param object    Must implement serializable. Also its sub-objects.
     * @return
     * @throws IOException
     */
    public static final String serialize(Serializable object) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( object );
        oos.close();
        BASE64Encoder base64Encoder =  new BASE64Encoder();
        return  new String( base64Encoder.encode(baos.toByteArray()));
    }
}
