import pickle
import numpy as np
from flask import Flask, request, jsonify, render_template


# Create flask app
app = Flask(__name__)
model = pickle.load(open("model.pkl", "rb"))

@app.route('/')
def index():
    return render_template("index.html")

@app.route("/predict", methods=['POST'])
def predict():
    float_features = [float(x) for x in request.form.values()]
    features = [np.array(float_features)]
    prediction = model.predict(features)[0]
    return render_template("index.html", prediction_text = "The Crop is {}".format(prediction))

@app.route('/api/predict', methods=['POST'])
def predictApi():
    n = request.form.get('n')
    p = request.form.get('p')
    k = request.form.get('k')
    temperature = request.form.get('temperature')
    humidity = request.form.get('humidity')
    ph = request.form.get('ph')

    input_query = np.array([[n, p, k, temperature, humidity, ph]])
    result = model.predict(input_query)[0]
    return jsonify({'predicted':str(result)})

if __name__ == '__main__':
    app.run()
    #app.run(host='192.168.0.215', port=5000, debug=False, threaded=True)