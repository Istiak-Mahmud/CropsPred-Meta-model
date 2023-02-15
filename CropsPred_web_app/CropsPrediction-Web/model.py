import pandas as pd
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import RandomForestClassifier
from sklearn.naive_bayes import GaussianNB
from sklearn.model_selection import train_test_split
import pickle

# Load the csv file
df = pd.read_csv("Crop_recommendation.csv")

print(df.head())

# Select independent and dependent variable
X = df[["N", "P", "K", "temperature", "humidity", "ph"]]
y = df["label"]



# Split the dataset into train and test
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=5)

# Feature scaling
sc = StandardScaler()
X_train = sc.fit_transform(X_train)
X_test= sc.transform(X_test)

# Instantiate the model
#classifier = RandomForestClassifier()
classifier = GaussianNB()

# Fit the model
classifier.fit(X_train, y_train)

# Make pickle file of our model
pickle.dump(classifier, open("model.pkl", "wb"))

##--------------------------

model = []
model.append(('BaysNa', GaussianNB()))

from sklearn.metrics import accuracy_score

scoring = 'accuracy'
for name, model in model:
  Y_pred = model.fit(X_train, y_train).predict(X_test)
  print(name)
  print('Accuracy score: %.2f'
        % accuracy_score(y_test, Y_pred))

##--------------------------