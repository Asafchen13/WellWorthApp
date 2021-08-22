package com.example.loginregisterfirebase.managers;

import android.util.Log;
import android.widget.TextView;

import com.example.loginregisterfirebase.logic.Cryptocurrency;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class CryptoAPIManager {

//
//    private final String BASE_URL = "http://api.coinlayer.com/list?access_key=ff1a3b7405800c23e97c37981ea6c6bcc";
//    private final String BASE_URL1 = "https://api.nomics.com/v1/markets?key=150f7007b20fe2dbe239bd5b6efdf068cd25a238";
    private static final String DATA = "data";
    private static final String ID = "id";
    private static final String CHANGE_PERCENT = "changePercent24Hr";
    private static final String PRICE = "priceUsd";


    private final String BASE_URL = "https://api.coincap.io/v2/assets";

    private static CryptoAPIManager instance = null;


    private CryptoAPIManager() {
    }

    public static CryptoAPIManager getInstance() {
        if (instance == null) {
            instance = new CryptoAPIManager();
        }
        return instance;
    }


    public void getData(String crypto_symbol_name, CryptoApiCallBack cryptoApiCallBack) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = BASE_URL + "/" + crypto_symbol_name;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Map<String, Object> map = new HashMap<>();
                    JSONObject response = new JSONObject(new String(responseBody));
                    JSONObject data = response.getJSONObject(DATA);
                    map.put(DatabaseManager.CRYPTO_ID, data.get(ID));
                    map.put(DatabaseManager.CRYPTO_PRICE, data.get(PRICE));
                    map.put(DatabaseManager.CRYPTO_CHNG_PERC, data.get(CHANGE_PERCENT));

                    cryptoApiCallBack.onCallBack(map);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public interface CryptoApiCallBack{
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