package keystrokesmod.client.utils.version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class VersionManager {

    private Version latestVersion;
    private Version clientVersion;

    public VersionManager() {
        createClientVersion();
        createLatestVersion();
    }

    private void createLatestVersion() {
        String version = "1.0.0";
        String branch = "";
        int branchCommit = 0;

        this.latestVersion = new Version(version, branch, branchCommit);
    }

    private void createClientVersion(){
        String version = "1.0.0";
        String branch = "";
        int branchCommit = 0;

        this.clientVersion = new Version(version, branch, branchCommit);
    }

    public Version getClientVersion(){
        return clientVersion;
    }

    public Version getLatestVersion(){
        return latestVersion;
    }
}
