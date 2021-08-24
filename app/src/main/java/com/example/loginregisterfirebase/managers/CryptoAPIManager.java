package com.example.loginregisterfirebase.managers;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.loginregisterfirebase.logic.Cryptocurrency;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import cz.msebera.android.httpclient.Header;

public class CryptoAPIManager {

    public static final String TAG = "CRYPTO_API_MANAGER";

    public static final String DATA = "data";
    public static final String ID = "id";
    public static final String CHANGE_PERCENT = "changePercent24Hr";
    public static final String PRICE = "priceUsd";


    private final String BASE_URL = "https://api.coincap.io/v2/assets";
    private final Executor executor;
    private final Handler responseHandler;
    private final Activity activity;

    public CryptoAPIManager(Executor executor, Handler responseHandler, Activity activity) {
        this.executor = executor;
        this.responseHandler = responseHandler;
        this.activity = activity;
    }

    public void makeCoinDataRequest(Cryptocurrency c, CryptoApiCallBack apiCallBack) {

        executor.execute(() -> {
            Looper.prepare();
            try {
                String cId = c.getId();
                String url = BASE_URL + "/" + cId;
                Map<String, Object> responseMap = new HashMap<>();
                SyncHttpClient client1 = new SyncHttpClient();
                client1.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Log.d(TAG, "makeCoinDataRequest() ,onSuccess : statusCode : " + statusCode);
                        try {
                            JSONObject response = new JSONObject(new String(responseBody));
                            JSONObject data = response.getJSONObject(DATA);
                            responseMap.put(DatabaseManager.CRYPTO_ID, data.get(ID));
                            responseMap.put(DatabaseManager.CRYPTO_PRICE, data.get(PRICE));
                            responseMap.put(DatabaseManager.CRYPTO_CHNG_PERC, data.get(CHANGE_PERCENT));
                            notifyResult(responseMap, apiCallBack);
                        } catch (JSONException e) {
                            Log.e(TAG, "makeCoinDataRequest() : failed parse JSONObject");
                            notifyResult(null, apiCallBack);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.e(TAG, " makeCoinDataRequest() failed : onFailure " + error.getMessage() +
                                ", statusCode :" + statusCode);
                        notifyResult(null, apiCallBack);
                        activity.runOnUiThread(() -> Toast.makeText(
                                activity,
                                error.getMessage() + ", try again in few seconds",
                                Toast.LENGTH_SHORT)
                                .show());
                    }
                });


            } catch (Exception e) {
                Log.e(TAG, " makeCoinDataRequest() failed : " + e.getMessage());
                notifyResult(null, apiCallBack);
            }
        });

    }

    private void notifyResult(Map<String, Object> map,
                              CryptoApiCallBack apiCallBack) {
        responseHandler.post(() -> apiCallBack.onCallBack(map));
    }

    public interface CryptoApiCallBack {
        void onCallBack(Map<String, Object> map);
    }
}

/**
 * bitcoin
 * ethereum
 * cardano
 * binance-coin
 * tether
 * xrp
 * dogecoin
 * usd-coin
 * polkadot
 * solana
 * uniswap
 * terra-luna
 * binance-usd
 * bitcoin-cash
 * chainlink
 * litecoin
 * internet-computer
 * wrapped-bitcoin
 * polygon
 * ethereum-classic
 * stellar
 * vechain
 * theta
 * filecoin
 * multi-collateral-dai
 * tron
 * avalanche
 * aave
 * cosmos
 * eos
 * monero
 * pancakeswap
 * ftx-token
 * klaytn
 * axie-infinity
 * the-graph
 * crypto-com-coin
 * neo
 * maker
 * bitcoin-bep2
 * algorand
 * shiba-inu
 * tezos
 * bitcoin-sv
 * unus-sed-leo
 * elrond-egld
 * iota
 * bittorrent
 * amp
 * waves
 * kusama
 * compound
 * huobi-token
 * terrausd
 * quant
 * dash
 * hedera-hashgraph
 * decred
 * fei-protocol
 * helium
 * near-protocol
 * chiliz
 * xinfin-network
 * counos-x
 * thorchain
 * holo
 * zcash
 * nem
 * theta-fuel
 * sushiswap
 * audius
 * celsius
 * decentraland
 * trueusd
 * yearn-finance
 * synthetix-network-token
 * ravencoin
 * enjin-coin
 * qtum
 * voyager-token
 * okb
 * fantom
 * zilliqa
 * basic-attention-token
 * bitcoin-gold
 * arweave
 * harmony
 * nexo
 * safemoon
 * telcoin
 * bancor
 * digibyte
 * ontology
 * revain
 * celo
 * siacoin
 * defichain
 * mdex
 * 0x
 * perpetual-protocol
 */