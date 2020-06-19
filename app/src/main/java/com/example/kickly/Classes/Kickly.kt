package com.example.kickly.Classes

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Icon
import android.os.AsyncTask
import android.util.Log
import com.example.kickly.Team
import com.example.kickly.Tournament
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset


class Kickly : android.app.Application() {

    companion object {

        var tournamentList = ArrayList<Tournament>()
        var locationList = ArrayList<Location>()
        var iconList = ArrayList<Icon>()
        var teamList = ArrayList<Team>()
        var createCode = 1
        var editCode = 2
        var finishCode = 3
        var selectCode = 4
        var selectTeam1Code = 5
        var selectTeam2Code = 6
        var context : Context? = null
        var BASE_API = "http://kickly.ddns.net/api"

        private val client = OkHttpClient()

        //region location

        // load locations
        private class LoadLocationListFromAPIAsync : AsyncTask<Void, Void, String>(){

            var path = "/local"
            var jsonArray : JSONArray? = null

            override fun doInBackground(vararg params: Void?): String {

                // get URL for API call
                val url = URL(BASE_API + path)

                // get URL content (JSON)
                val urlContent = url.readText(Charset.defaultCharset())
                Log.d("locais", urlContent)

                return urlContent

            }

            override fun onPostExecute(urlContent: String) {
                super.onPostExecute(urlContent)

                jsonArray = JSONArray(urlContent)

                locationList = ArrayList<Location>()

                for (i in 0 until jsonArray!!.length()) {
                    val obj: JSONObject = jsonArray!!.getJSONObject(i)
                    var name = obj.getString("str_name")
                    var databaseID = obj.getInt("id")
                    locationList.add(Location(name, databaseID))
                }
            }
        }

        fun loadLocationsFromAPI() {

            // load locations
                LoadLocationListFromAPIAsync().execute(null, null, null)

        }

        // post location
        private class PostLocationToAPIAsync : AsyncTask<Location, Void, String>(){

            //var path = "/local"
            //var jsonArray : JSONArray? = null

            override fun doInBackground(vararg params: Location?): String {

                postLocationToAPIPrivate(params.first()!!)
                /*

                var locationID: Int = params.first()!!
                var locationName = locationList[locationID!!].name

                val json = JSONObject()
                json.put("str_name", locationName)


                // get URL for API call
                val url = URL(BASE_API + path)

                var out : OutputStream? = null

                try {
                    val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                    urlConnection.requestMethod = "POST"
                    out = urlConnection.outputStream
                    out = BufferedOutputStream(out!!)
                    val writer = BufferedWriter(OutputStreamWriter(out, "UTF-8"))
                    writer.write(json.toString())
                    writer.flush()
                    writer.close()
                    out.close()
                    urlConnection.connect()
                } catch (e: Exception) {
                    throw e
                }


*/
                return ""
            }

            override fun onPostExecute(urlContent: String) {
                super.onPostExecute(urlContent)
                loadLocationsFromAPI()

            }

        }
        private fun postLocationToAPIPrivate(location : Location) {

/*
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("str_name", "etdfh")
                .build()
*/
            val json = JSONObject()
            json.put("str_name", location.name)


            var requestBody : RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString())

            var request = Request.Builder()
                .url("http://kickly.ddns.net/api/local")
                .post(requestBody)
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful()) throw IOException("Unexpected code $response")
                val responseHeaders: Headers = response.headers()
                for (i in 0 until responseHeaders.size()) {
                    println(responseHeaders.name(i).toString() + ": " + responseHeaders.value(i))
                }
                Log.e("POST", response.body()!!.string()!!)
            }
        }

        fun postLocationToAPI(location: Location) {

            // load locations
                PostLocationToAPIAsync().execute(location, null, null)

        }

        // put location
        private class PutLocationToAPIAsync : AsyncTask<Location, Void, String>(){

            //var path = "/local"
            //var jsonArray : JSONArray? = null

            override fun doInBackground(vararg params: Location?): String {

                putLocationToAPIPrivate(params.first()!!)
                /*

                var locationID: Int = params.first()!!
                var locationName = locationList[locationID!!].name

                val json = JSONObject()
                json.put("str_name", locationName)


                // get URL for API call
                val url = URL(BASE_API + path)

                var out : OutputStream? = null

                try {
                    val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                    urlConnection.requestMethod = "POST"
                    out = urlConnection.outputStream
                    out = BufferedOutputStream(out!!)
                    val writer = BufferedWriter(OutputStreamWriter(out, "UTF-8"))
                    writer.write(json.toString())
                    writer.flush()
                    writer.close()
                    out.close()
                    urlConnection.connect()
                } catch (e: Exception) {
                    throw e
                }


*/
                return ""
            }

            override fun onPostExecute(urlContent: String) {
                super.onPostExecute(urlContent)
                loadLocationsFromAPI()
            }

        }
        private fun putLocationToAPIPrivate(location : Location) {

/*
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("str_name", "etdfh")
                .build()
*/
            val json = JSONObject()
            json.put("str_name", location.name)


            var requestBody : RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString())

            var request = Request.Builder()
                .url("http://kickly.ddns.net/api/local/" + location.databaseID)
                .put(requestBody)
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful()) throw IOException("Unexpected code $response")
                val responseHeaders: Headers = response.headers()
                for (i in 0 until responseHeaders.size()) {
                    println(responseHeaders.name(i).toString() + ": " + responseHeaders.value(i))
                }
                Log.e("POST", response.body()!!.string()!!)
            }
        }

        fun putLocationToAPI(location: Location) {

            // load locations
                PutLocationToAPIAsync().execute(location, null, null)

        }

        //endregion

        //region team

        // load teams
        private class LoadTeamListFromAPIAsync : AsyncTask<Void, Void, String>(){

            var path = "/equipas"
            var jsonArray : JSONArray? = null

            override fun doInBackground(vararg params: Void?): String {

                // get URL for API call
                val url = URL(BASE_API + path)

                // get URL content (JSON)
                val urlContent = url.readText(Charset.defaultCharset())
                Log.d("equipas", urlContent)

                return urlContent

            }

            override fun onPostExecute(urlContent: String) {
                super.onPostExecute(urlContent)

                jsonArray = JSONArray(urlContent)

                teamList = ArrayList<Team>()

                for (i in 0 until jsonArray!!.length()) {
                    val obj: JSONObject = jsonArray!!.getJSONObject(i)
                    var name = obj.getString("nome")
                    var databaseID = obj.getInt("id")
                    var iconID = obj.getInt("id_icon")
                    teamList.add(Team( iconList[iconID], name, databaseID))
                }
            }
        }

        fun loadTeamsFromAPI() {

            // load teams
            LoadTeamListFromAPIAsync().execute(null, null, null)

        }

        // post team
        private class PostTeamToAPIAsync : AsyncTask<Team, Void, String>(){

            //var path = "/local"
            //var jsonArray : JSONArray? = null

            override fun doInBackground(vararg params: Team?): String {

                postTeamToAPIPrivate(params.first()!!)
                /*

                var locationID: Int = params.first()!!
                var locationName = locationList[locationID!!].name

                val json = JSONObject()
                json.put("str_name", locationName)


                // get URL for API call
                val url = URL(BASE_API + path)

                var out : OutputStream? = null

                try {
                    val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                    urlConnection.requestMethod = "POST"
                    out = urlConnection.outputStream
                    out = BufferedOutputStream(out!!)
                    val writer = BufferedWriter(OutputStreamWriter(out, "UTF-8"))
                    writer.write(json.toString())
                    writer.flush()
                    writer.close()
                    out.close()
                    urlConnection.connect()
                } catch (e: Exception) {
                    throw e
                }


*/
                return ""
            }

            override fun onPostExecute(urlContent: String) {
                loadTeamsFromAPI()
                super.onPostExecute(urlContent)

            }

        }
        private fun postTeamToAPIPrivate(team : Team) {

/*
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("str_name", "etdfh")
                .build()
*/
            val json = JSONObject()
            json.put("nome", team.name)
            json.put("id_icon", getIconID(team.icon))


            var requestBody : RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString())

            var request = Request.Builder()
                .url("http://kickly.ddns.net/api/equipas")
                .post(requestBody)
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful()) throw IOException("Unexpected code $response")
                val responseHeaders: Headers = response.headers()
                for (i in 0 until responseHeaders.size()) {
                    println(responseHeaders.name(i).toString() + ": " + responseHeaders.value(i))
                }
                Log.e("POST", response.body()!!.string()!!)
            }
        }

        fun postTeamToAPI(team: Team) {

            // load locations
            PostTeamToAPIAsync().execute(team, null, null)

        }

        // put team
        private class PutTeamToAPIAsync : AsyncTask<Team, Void, String>(){

            //var path = "/local"
            //var jsonArray : JSONArray? = null

            override fun doInBackground(vararg params: Team?): String {

                putTeamToAPIPrivate(params.first()!!)
                /*

                var locationID: Int = params.first()!!
                var locationName = locationList[locationID!!].name

                val json = JSONObject()
                json.put("str_name", locationName)


                // get URL for API call
                val url = URL(BASE_API + path)

                var out : OutputStream? = null

                try {
                    val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                    urlConnection.requestMethod = "POST"
                    out = urlConnection.outputStream
                    out = BufferedOutputStream(out!!)
                    val writer = BufferedWriter(OutputStreamWriter(out, "UTF-8"))
                    writer.write(json.toString())
                    writer.flush()
                    writer.close()
                    out.close()
                    urlConnection.connect()
                } catch (e: Exception) {
                    throw e
                }


*/
                return ""
            }

            override fun onPostExecute(urlContent: String) {
                super.onPostExecute(urlContent)
                loadTeamsFromAPI()
            }

        }
        private fun putTeamToAPIPrivate(team: Team) {

/*
            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("str_name", "etdfh")
                .build()
*/
            val json = JSONObject()
            json.put("nome", team.name)
            json.put("id_icon", getIconID(team.icon))


            var requestBody : RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString())

            var request = Request.Builder()
                .url("http://kickly.ddns.net/api/equipas/" + team.databaseID)
                .put(requestBody)
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful()) throw IOException("Unexpected code $response")
                val responseHeaders: Headers = response.headers()
                for (i in 0 until responseHeaders.size()) {
                    println(responseHeaders.name(i).toString() + ": " + responseHeaders.value(i))
                }
                Log.e("POST", response.body()!!.string()!!)
            }
        }

        fun putTeamToAPI(team: Team) {

            // load locations
            PutTeamToAPIAsync().execute(team, null, null)

        }

        //endregion

        // check data
        fun checkData() {
            if (locationList.isEmpty()) {loadLocationsFromAPI()}
        }

        fun getIconID(icon : Icon) : Int {

            var iconID : Int? = null

            for (i in 0 until iconList.size) {
                if (icon == iconList[i]) {
                    iconID = i
                }

                if (iconID == null) {
                    throw (Exception("icon not found in iconList"))
                }
            }
            return iconID!!
        }

    }

}