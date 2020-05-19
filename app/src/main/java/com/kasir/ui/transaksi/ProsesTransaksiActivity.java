package com.kasir.ui.transaksi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.kasir.R;
import com.kasir.adapter.TransaksiAdapter;
import com.kasir.data.model.Barang;
import com.kasir.data.model.Cart;
import com.kasir.data.model.TransaksiDetail;
import com.kasir.ui.barang.BarangViewModel;
import com.kasir.ui.dashboard.DashboardActivity;
import com.kasir.ui.history.HistoryViewModel;
import com.kasir.ui.transaksiDetail.TransaksiDetailViewModel;
import com.kasir.util.StringAlignUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ProsesTransaksiActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button btnTran;
    private TextView test;
    private TransaksiViewModel transaksiViewModel;
    private TransaksiDetailViewModel transaksiDetailViewModel;
    private BarangViewModel barangViewModel;
    private HistoryViewModel historyViewModel;
    private TransaksiAdapter adapter;

    private String namaTranDet;
    private int jumlahBeli;
    private int jumlahHarga;
    private int harga;
    private int jumlahAkhir;
    private int jumlahMax;
    private int idBarang;
    private int totalHarga;
    private int totalUang;
    private int kembalian;

    private TransaksiDetail transaksiDetail = null;
    public static final String EXTRA_IDHIS = "idHis";
    public static final String EXTRA_TOTALHARGA = "totalHarga";
    public static final String EXTRA_TOTALUANG = "totalUang";
    public static final String EXTRA_KEMBALIAN = "kembalian";

    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket socket;
    BluetoothDevice bluetoothDevice;
    OutputStream outputStream;
    InputStream inputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    String value = "";

    StringAlignUtils alignCenter;
    StringAlignUtils alignRight;

    StringBuilder printNama = new StringBuilder() ;
    StringBuilder printJumlah = new StringBuilder() ;
    StringBuilder printHarga = new StringBuilder() ;
    StringBuilder printItem = new StringBuilder() ;

    String pJumlah, pHarga;

    TableRow tableRow;
    TableLayout tableLayout;
    int n = 10;
    TextView[] tvStruk = new TextView[n];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses_transaksi);

        test = findViewById(R.id.test);
        tableLayout = findViewById(R.id.table);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new TransaksiAdapter();
        recyclerView.setAdapter(adapter);

        transaksiViewModel = ViewModelProviders.of(this).get(TransaksiViewModel.class);
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        transaksiDetailViewModel = ViewModelProviders.of(this).get(TransaksiDetailViewModel.class);
        barangViewModel = ViewModelProviders.of(this).get(BarangViewModel.class);

        transaksiViewModel.getAllCarts().observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                adapter.submitList(carts);
            }
        });

        Intent in = getIntent();
        final int idHis = in.getIntExtra(EXTRA_IDHIS, 1);
        totalHarga = in.getIntExtra(EXTRA_TOTALHARGA, 0);
        totalUang = in.getIntExtra(EXTRA_TOTALUANG, 0);
        kembalian = in.getIntExtra(EXTRA_KEMBALIAN, 0);

        transaksiViewModel.getAllCarts().observe(ProsesTransaksiActivity.this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                String open = "Kasir\n";
                String separator = "--------------------\n";
                String header = String.format("%-15s" , "Nama Barang") + String.format("%5s", "Jml") + String.format("%10s", "Harga\n");
                String totalHar = "Total Harga : Rp." + String.valueOf(totalHarga) + "\n";
                String uang = "Tunai       : Rp." + String.valueOf(totalUang) + "\n";
                String kem = "Kembalian   : Rp." + String.valueOf(kembalian) + "\n";
                String tq = "Terima Kasih\n";
                String struck = "";
                String print = "";

                adapter.submitList(carts);
                for(int i=0; i<carts.size(); i++){
//                    TableRow row = new TableRow(ProsesTransaksiActivity.this);
                    if (carts.get(i).getNamaBarang().length() > 15 ) struck += String.format("%-15s" , carts.get(i).getNamaBarang().substring(0,15)) + String.format("%5s", String.valueOf(carts.get(i).getJumlahBeli())) + String.format("%10s", carts.get(i).getJumlahHarga()+"\n");
                    else struck += String.format("%-15s" , carts.get(i).getNamaBarang()) + String.format("%5s", String.valueOf(carts.get(i).getJumlahBeli())) + String.format("%10s", carts.get(i).getJumlahHarga()+"\n");
                    Log.i("strk", String.valueOf(i));
//
//                    TextView rowTv1 = new TextView(ProsesTransaksiActivity.this);
//                    TextView rowTv2 = new TextView(ProsesTransaksiActivity.this);
//                    TextView rowTv3 = new TextView(ProsesTransaksiActivity.this);
//                    rowTv1.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
//                    rowTv1.setGravity(Gravity.CENTER);
//                    rowTv2.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
//                    rowTv2.setGravity(Gravity.CENTER);
//                    rowTv3.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
//                    rowTv3.setGravity(Gravity.CENTER);

                    namaTranDet = carts.get(i).getNamaBarang();
                    printNama.append(namaTranDet);
//                    rowTv1.setText(namaTranDet);

                    jumlahBeli = carts.get(i).getJumlahBeli();
                    printJumlah.append(jumlahBeli);
//                    rowTv2.setText(String.valueOf(jumlahBeli));

                    jumlahHarga = carts.get(i).getJumlahHarga();
                    printHarga.append(jumlahHarga);
//                    rowTv3.setText(String.valueOf(jumlahHarga));

                    harga = carts.get(i).getHarga();
                    jumlahMax = carts.get(i).getJumlahBeliMax();
                    idBarang = carts.get(i).getIdBarang();
                    jumlahAkhir = jumlahMax - jumlahBeli;
                    totalHarga += jumlahHarga;

//                    alignCenter = new StringAlignUtils(30, StringAlignUtils.Alignment.CENTER);
//                    alignRight = new StringAlignUtils(5, StringAlignUtils.Alignment.RIGHT);
//                    pJumlah =  alignCenter.format(printJumlah);
//                    pHarga = alignRight.format(printHarga);

//                    row.addView(rowTv1);
//                    row.addView(rowTv2);
//                    row.addView(rowTv3);

//                    tableLayout.addView(row, i);

                    transaksiDetail = new TransaksiDetail(idHis, namaTranDet, jumlahBeli, jumlahHarga);
                    transaksiDetailViewModel.insert(transaksiDetail);

                    Barang barang = new Barang(namaTranDet, harga, jumlahAkhir);
                    barang.setId(idBarang);
                    barangViewModel.update(barang);

                    print = separator+open+separator+header+struck+totalHar+uang+kem+tq;
                }

//                printItem.append(printNama);
//                printItem.append(printJumlah);
//                printItem.append(printHarga);

                try {
                    IntentPrint(print);
                } catch (Exception e){
                    Toast.makeText(ProsesTransaksiActivity.this, "Printer doestn connected", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                test.setText(print);

                transaksiViewModel.deleteAll();
                startActivity(new Intent(ProsesTransaksiActivity.this, TransaksiActivity.class));
            }
        });
    }

    private void IntentPrint(String nama) {
//        String struck = nama+String.format("%5s", jumlah) + String.format("%5s", harga+"\n");
//        byte[] center = new byte[]{0x1b, 'a', 0x01};
//        byte[] left = new byte[]{0x1b, 'a', 0x00};
//        byte[] right = new byte[]{0x1b, 'a', 0x02};
//        byte[] cus = {Byte.parseByte(nama), Byte.parseByte(jumlah), Byte.parseByte(harga)};
//        Log.i("_struck",nama);

        InitPrinter();
            try {
//                outputStream.write(separator.getBytes());
//                outputStream.write(open.getBytes());
//                outputStream.write(separator.getBytes());
//                outputStream.write(header.getBytes());
//                outputStream.write(nama.getBytes());
//                outputStream.write(right);
//                outputStream.write(separator.getBytes());
//                outputStream.write(totalHar.getBytes());
//                outputStream.write(uang.getBytes());
//                outputStream.write(kem.getBytes());
//                outputStream.write(center);
//                outputStream.write(tq.getBytes());
                outputStream.write(nama.getBytes());
                outputStream.close();
                socket.close();
            }
            catch(Exception ex) {
                value+=ex.toString()+ "\n" +"Excep IntentPrint \n";
                Toast.makeText(ProsesTransaksiActivity.this, "Printer doestn connected", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }
    }

    private void InitPrinter() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try
        {
            if(!bluetoothAdapter.isEnabled())
            {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0)
            {
                for(BluetoothDevice device : pairedDevices)
                {
                    if(device.getName().equals("SMT-P58B")) //Note, you will need to change this to match the name of your device
                    {
                        bluetoothDevice = device;
                        break;
                    }
                }

                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
                Method m = bluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                socket = (BluetoothSocket) m.invoke(bluetoothDevice, 1);
                bluetoothAdapter.cancelDiscovery();
                socket.connect();
                outputStream = socket.getOutputStream();
                inputStream = socket.getInputStream();
                beginListenForData();
            }
            else
            {
                value+="No Devices found";
                Toast.makeText(this, value, Toast.LENGTH_LONG).show();
                return;
            }
        }
        catch(Exception ex)
        {
            value+=ex.toString()+ "\n" +" InitPrinter \n";
            Toast.makeText(ProsesTransaksiActivity.this, "Printer doestn connected", Toast.LENGTH_SHORT).show();
        }
    }

    private void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {
                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = inputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                inputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                Log.d("e", data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }
                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            Toast.makeText(ProsesTransaksiActivity.this, "Printer doestn connected", Toast.LENGTH_SHORT).show();
        }
    }
}
