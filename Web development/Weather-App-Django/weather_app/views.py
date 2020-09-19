from django.shortcuts import render
import requests
from .forms import CityForm
from .models import City


def index(request):
    url = 'http://api.openweathermap.org/data/2.5/weather?q={}&units=imperial&appid=913f0218380788963b36a68c0895c4c2'

    weather_data = "NONE"
    if request.method == 'POST':
        form = CityForm(request.POST)
        form.save()
        r = requests.get(url.format(request.POST['name'])).json()
        print(request.POST['name'])
        print(r)
        try:
            weather_data = {
                'city': request.POST['name'],
                'temperature': r['main']['temp'],
                'description': r['weather'][0]['description'],
                'icon': r['weather'][0]['icon'],
            }
        except KeyError:
            pass
    form = CityForm()
    context = {'weather_data': weather_data, 'form': form}

    return render(request, 'weather_app/index.html', context)


def getRestaurantList(ilocation, query):
    rtype = query
    result = {}
    restaurantList = []
    # Get latitude and longitude
    google_payload = {'address': ilocation, 'key': GOOGLE_API_KEY}
    rgoogle = requests.get("https://maps.googleapis.com/maps/api/geocode/json", params=google_payload)
    googlejson = rgoogle.json()
    print(rgoogle.url)
    status = googlejson["status"]
    if not status == "OK":
        print("Invalid address - Try again")
        result["error"] = "Invalid address - Try again"
        return result
    location = googlejson['results'][0]

    # print ("Latitude and Longitude of Seoul : ",location["geometry"]["location"])
    latitude = location["geometry"]["location"]["lat"]
    longitude = location["geometry"]["location"]["lng"]
    latlong = str(latitude) + "," + str(longitude)
    loc = Location(restaurant_location=ilocation, latitude=latitude, longitude=longitude, restaurant_type=query)
    loc.save()
    # Get restaurant based on query
    foursquare_payload = {'client_id': FOURSQUARE_CLIENT_ID, 'client_secret': FOURSQUARE_CLIENT_SECRET, 'v': 20160105,
                          'limit': 100, 'll': latlong, 'radius': "800", 'query': query}

    rfoursquare = requests.get("https://api.foursquare.com/v2/venues/search", params=foursquare_payload)
    print(rfoursquare)
    fjson = rfoursquare.json()
    try:
        restaurants = fjson['response']['venues']
    except KeyError:
        result["error"] = "Sorry - No Restaurant found!"
        return result

    total_restaurants = len(restaurants)
    restaurants_count = min(total_restaurants, 100)
    if not restaurants_count > 0:
        result["error"] = "Sorry - No Restaurant found!"
        return result
    for restaurant in restaurants:
        oneRestaurant = {}
        location = restaurant["location"]
        formattedAddress = []
        try:
            formattedAddress = location["formattedAddress"]
        except KeyError:
            formattedAddress[0] = "N/A"
        stats = restaurant["stats"]
        name = restaurant["name"]
        venue_id = restaurant["id"]
        checkIns = stats["checkinsCount"]
        contact = restaurant["contact"]
        phone_number = ''
        lat = location["lat"]
        lng = location["lng"]
        try:
            phone_number = contact["formattedPhone"]
        except KeyError:
            phone_number = 'N/A'
        oneRestaurant["name"] = name
        oneRestaurant["checkins"] = checkIns
        oneRestaurant["phone_number"] = phone_number
        oneRestaurant["venue_id"] = venue_id
        oneRestaurant["address"] = (' ').join(formattedAddress)
        oneRestaurant["lat"] = lat
        oneRestaurant["lng"] = lng
        # Get Restaurant photo
        foursquare_payload_photo = {'client_id': FOURSQUARE_CLIENT_ID, 'client_secret': FOURSQUARE_CLIENT_SECRET,
                                    'v': 20160105}

        rfoursquare_photo = requests.get("https://api.foursquare.com/v2/venues/" + venue_id + "/photos",
                                         params=foursquare_payload_photo)
        # print (rfoursquare_photo.url)
        photojson = rfoursquare_photo.json()
        print("photojson")
        print(photojson)
        photos = photojson['response']['photos']
        total_photos = photos["count"]
        if total_photos > 0:
            photos_count = min(total_photos, 100)
            random_photo_index = random.randrange(0, photos_count)
            photo = photos['items'][random_photo_index]
            photo_url = photo["prefix"] + "455x300" + photo["suffix"]
            # print (photo_url)
            oneRestaurant["photo_url"] = photo_url
            try:
                rest = loc.restaurant.get(name=name, address=oneRestaurant["address"])

            except ObjectDoesNotExist:
                try:
                    rest = Restaurant.objects.get(name=name, address=oneRestaurant["address"], r_type=rtype)
                    rest.checkins = checkIns
                    rest.phone_number = phone_number
                    rest.save()
                    loc.restaurant.add(rest)
                    restaurantList.append(oneRestaurant)
                    print("exisiting rest found and added to location")
                except ObjectDoesNotExist:
                    rest = Restaurant(name=name, latitude=lat, longitude=lng, checkins=checkIns,
                                      phone_number=phone_number,
                                      venue_id=venue_id, address=oneRestaurant["address"], photo_url=photo_url,
                                      r_type=rtype)
                    rest.save()
                    loc.restaurant.add(rest)
                    restaurantList.append(oneRestaurant)
                    print("no rest found therefore saved and added to location")
                except MultipleObjectsReturned:
                    print("multiple objects returned 1")
            except MultipleObjectsReturned:
                print("multiple objects returned 2")
        else:
            # oneRestaurant["image"] = None
            print("no_photo")

    try:
        loc = Location.objects.get(restaurant_location=ilocation, restaurant_type=rtype)
    except MultipleObjectsReturned:
        loc = loc[0]
    restaurants = loc.restaurant.all()
    result["rlist"] = restaurants
    return result
