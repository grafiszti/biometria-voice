package pl.biometria.voice.db;

import com.google.common.collect.Lists;

import pl.biometria.voice.Constants;
import pl.biometria.voice.recognition.utils.NamedVoicePrint;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class NamedVoicePrintDao {
    public List<NamedVoicePrint> findAll() {
        List<NamedVoicePrint> resultList = Lists.newArrayList();

        try {
            FileInputStream f_in = new FileInputStream(Constants.DB_FILENAME);
            ObjectInputStream obj_in = new ObjectInputStream(f_in);

            for (; ; ) {
                Object obj = obj_in.readObject();
                if (obj != null && obj instanceof NamedVoicePrint) {
                    NamedVoicePrint voicePrint = (NamedVoicePrint) obj;
                    resultList.add(voicePrint);
                } else {
                    break;
                }
            }
            obj_in.close();
            f_in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public void saveAll(List<NamedVoicePrint> namedVoicePrintsToStore) {
        try {
            FileOutputStream f_out = new FileOutputStream(Constants.DB_FILENAME);
            ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
            for (NamedVoicePrint voicePrint : namedVoicePrintsToStore) {
                obj_out.writeObject(voicePrint);
            }
            obj_out.close();
            f_out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
