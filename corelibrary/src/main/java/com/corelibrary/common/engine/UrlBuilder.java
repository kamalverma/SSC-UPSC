package com.corelibrary.common.engine;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by kamal on 26/12/2016.
 */
public abstract class UrlBuilder {

    public static final String TAG = UrlBuilder.class.getSimpleName();

    public static String getSubjectListUrl(String appId) {
        String url = NetworkUtils.getIxigoPrefixHost() + "/rest/v1/category/list";
        if (appId != null) {
            url += "?appId=" + appId;
        }
        return url;
    }

    public static String getHomePageMoreAdUrl(int catId) {
        String url = NetworkUtils.getIxigoPrefixHost() + "/apis/v3/ads/train/home?category=" + catId;
        return url;
    }

    public static String getPredictionUrl() {
        return NetworkUtils.getIxigoPrefixHost() + "/pnrprediction/";
    }

    public static String getDelayDataUrl() {
        return NetworkUtils.getIxigoPrefixHost() + "/api/v2/trains/delay";
    }


    public static String getHotelSummaryAroundStation(String stationCode) throws UnsupportedEncodingException {
        return NetworkUtils.getIxigoPrefixHost() + "/rest/trains/hotelInfo/" + URLEncoder.encode(stationCode, "UTF-8");
    }

    public static String getPlatformLocatorUgcUrl() {
        return NetworkUtils.getIxigoPrefixHost() + "/api/v2/trains/platform";
    }

    public static String getMetroRouteUrl(String orgCode, String destCode) {
        return NetworkUtils.getIxigoPrefixHost() + "/ixi-api/metro/routes?source=" + orgCode + "&destination=" + destCode;
    }

    public static String getLocalTrainBetweenUrl(String orgCode, String destCode) {
        return NetworkUtils.getIxigoPrefixHost()
                + "/ixi-api/v2/local/trains?source=" + orgCode + "&destination=" + destCode;
    }

    public static String getLocalTrainRouteUrl(String trainNumber, String orgCode, String dstCode) {
        return NetworkUtils.getIxigoPrefixHost() + "/ixi-api/v2/local/train/" + trainNumber + "?source=" + orgCode + "&destination=" + dstCode;
    }

    public static String getLocalMultipleTrainRouteUrl(String trainNumbers, String orgCodes, String dstCodes) {
        return NetworkUtils.getIxigoPrefixHost() + "/ixi-api/v2/local/route?train=" + trainNumbers + "&source=" + orgCodes + "&destination=" + dstCodes;
    }

    public static String getMetroStationsUrl(String cityName) {
        return NetworkUtils.getIxigoPrefixHost() + "/ixi-api/metro/stations?city=" + cityName;
    }

    public static String getLocalStationsUrl(String cityName) {
        return NetworkUtils.getIxigoPrefixHost() + "/ixi-api/v2/local/stations?city=" + cityName;
    }


    public static String getTrainNewsUrl(int pageNumber) {
        StringBuilder builder = new StringBuilder("http://wpcms.ixigo.com/?json=get_category_posts&slug=train-stories&status=publish");

        builder.append("&count=10");
        builder.append("&page=" + pageNumber);
        return builder.toString();
    }

    public static String getPostDetail(String postId) {
        StringBuilder builder = new StringBuilder("http://wpcms.ixigo.com/?json=get_post");
        builder.append("&id=" + postId);
        return builder.toString();
    }

    public static String getRefundCalculationUrl() {
        return NetworkUtils.getIxigoPrefixHost() + "/api/v2/trains/cancellationCharge";
    }

    public static String getTrainAvailabilityUrl() {
        return NetworkUtils.getIxigoPrefixHost() + "/api/v2/trains/availability";
    }


    public static String getAccessTokenUrl() {
        return NetworkUtils.getIxigoPrefixHost() + "/api/v2/cabs/token";
    }


}

