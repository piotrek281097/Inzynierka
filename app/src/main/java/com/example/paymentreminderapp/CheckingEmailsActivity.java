package com.example.paymentreminderapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.tom_roush.pdfbox.cos.COSDocument;
import com.tom_roush.pdfbox.io.RandomAccessFile;
import com.tom_roush.pdfbox.pdfparser.PDFParser;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

public class CheckingEmailsActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    TextView subject;
    TextView from;
    TextView text;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            System.out.println("Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_emails);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        subject = findViewById(R.id.subject);
        from = findViewById(R.id.from);
        text = findViewById(R.id.textEmail);

        System.out.println("CHECKING_EMAILS_ACTIVITY");

        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        String port = "995";
        String username = "testPaymentReminderApp1@gmail.com";// change accordingly
        String password = "norbert2807";// change accordingly


        isStoragePermissionGranted();
        //check(host, mailStoreType, username, password);
        downloadEmailAttachments(host, port, username, password);

        readAttachment();
    }

    private void readAttachment() {
        try {
            String yourFilePath = getApplicationContext().getExternalFilesDir(null) + "/" + "faktura2.pdf";
            File yourFile = new File(yourFilePath);

            System.out.println("po your file");

/*
            PDFParser parser = null;
            PDDocument pdDoc = null;
            COSDocument cosDoc = null;
            PDFTextStripper pdfStripper;

            String parsedText;
            String fileName = "E:\\Files\\Small Files\\PDF\\JDBC.pdf";
            File file = new File(yourFilePath);
            try {
                parser = new PDFParser(new RandomAccessFile(file));
                parser.parse();
                cosDoc = parser.getDocument();
                pdfStripper = new PDFTextStripper();
                pdDoc = new PDDocument(cosDoc);
                parsedText = pdfStripper.getText(pdDoc);
                System.out.println(parsedText.replaceAll("[^A-Za-z0-9. ]+", ""));
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    if (cosDoc != null)
                        cosDoc.close();
                    if (pdDoc != null)
                        pdDoc.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
/*
            try (BufferedReader br = new BufferedReader(new FileReader(yourFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // process the line.
                    System.out.println(line);
                }
            }*/
/*
            FileInputStream fis = getApplicationContext().openFileInput("faktura2.pdf");
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader bufferedReader = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                System.out.println(line);
            }*/

            String parsedText = "";
            PdfReader reader = new PdfReader(yourFilePath);
            int n = reader.getNumberOfPages();
            for (int i = 0; i < n; i++) {
                parsedText = parsedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n"; //Extracting the content from the different pages
            }
            //System.out.println(parsedText);
            saveFileTxt(parsedText);
            readFileTxt();
            reader.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void readFileTxt() {
        try {

            File file = new File(getExternalFilesDir(null), "faktura2.txt");


            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                System.out.println(line);
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
           // String everything = sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void check(String host, String storeType, String user,
                             String password) {
        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());

            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadEmailAttachments(String host, String port,
                                         String userName, String password) {
        Properties properties = new Properties();

        // server setting
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", port);

        // SSL setting
        properties.setProperty("mail.pop3.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.pop3.socketFactory.fallback", "false");
        properties.setProperty("mail.pop3.socketFactory.port",
                String.valueOf(port));

        Session session = Session.getDefaultInstance(properties);

        try {
            // connects to the message store
            Store store = session.getStore("imaps");
            store.connect("smtp.gmail.com", userName, password);

            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);

            // fetches new messages from server
            Message[] arrayMessages = folderInbox.getMessages();

            System.out.println("messages.length---" + arrayMessages.length);

            for (int i = 0; i < arrayMessages.length; i++) {
                Message message = arrayMessages[i];
                Address[] fromAddress = message.getFrom();
                String from = fromAddress[0].toString();
                String subject = message.getSubject();
                String sentDate = message.getSentDate().toString();

                String contentType = message.getContentType();
                String messageContent = "";

                // store attachment file name, separated by comma
                String attachFiles = "";

                if (contentType.contains("multipart")) {
                    // content may contain attachments
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            // this part is attachment
                            String fileName = "faktura2.pdf";
                            attachFiles += fileName + ", ";
                            System.out.println("FILENAME ________" + fileName);
                            //part.saveFile(fileName);
                            saveAttachment(part, fileName);

                        } else {
                            // this part may be the message content
                            messageContent = part.getContent().toString();
                        }
                    }

                    if (attachFiles.length() > 1) {
                        attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                    }
                } else if (contentType.contains("text/plain")
                        || contentType.contains("text/html")) {
                    Object content = message.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                }

                // print out details of each message
                System.out.println("Message #" + (i + 1) + ":");
                System.out.println("\t From: " + from);
                System.out.println("\t Subject: " + subject);
                System.out.println("\t Sent Date: " + sentDate);
                System.out.println("\t Message: " + messageContent);
                System.out.println("\t Attachments: " + attachFiles);
            }

            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for pop3.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveAttachment(MimeBodyPart mimeBodyPart, String fileName) {
        File path = Environment.getDownloadCacheDirectory();

        //File myDir = new File(root + "/attach" + fileName);
        path.mkdirs();

        System.out.println("PATH___________________ " + getExternalFilesDir(null));

        File file = new File(getExternalFilesDir(null), fileName);

        try {
            mimeBodyPart.saveFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveFileTxt(String text) {

        File file = new File(getExternalFilesDir(null), "faktura2.txt");

        try {
            PrintWriter out = new PrintWriter(file);
            out.print(text);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                System.out.println("Permission is granted");
                return true;
            } else {

                System.out.println("Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            System.out.println("Permission is granted");
            return true;
        }
    }
}
