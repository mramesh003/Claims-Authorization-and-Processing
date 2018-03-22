from flask import Flask
import Claims_A
app = Flask(__name__)

@app.route('/saveModel/<fileid>')
def save_model(fileid):
   Claim_adjudication = Claims_A.claim_adjudication()
   result = Claim_adjudication.train_model(fileid)
   print(result)
   return result;
@app.route('/evaluate/<fileid>')
def evaluate_model(fileid):
   Claim_adjudication = Claims_A.claim_adjudication()
   dtc1 = Claim_adjudication.execute_model(fileid)
   prediction = ' '.join(str(e) for e in dtc1)
   return prediction


if __name__ == '__main__':
   app.run(debug=True)