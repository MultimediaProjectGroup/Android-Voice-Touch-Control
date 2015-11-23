package ipc;

import java.io.OutputStream;

/**
 * Created by sam on 15/10/15.
 */
public class RootShellCmd {
    private OutputStream os;

    public final void exec(String cmd) {
        try {
            if (os == null) {
                // 申请获取root权限，这一步很重要，不然会没有作用
                os = Runtime.getRuntime().exec("su").getOutputStream();
            }
            os.write(cmd.getBytes());
            if (!cmd.trim().endsWith("\n")) {
                os.write("\n".getBytes());
            }
            os.flush();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void simulateKey(int keyCode) {
        exec("input keyevent " + keyCode + "\n");
    }

    public final void simulateSwip(String direction) {
        exec("input swipe " + direction + "\n");
    }

    public final void simulateTap(String direction) {
        exec("input tap " + direction + "\n");
    }
}
