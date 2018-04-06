from flask import Flask
import com.accenture.MLengine.claim_process_engine as claim_process_engine
app = Flask(__name__)

@app.route('/saveModel/<fileid>')
def save_model(fileid):
   Claim_adjudication = claim_process_engine.claim_adjudication()
   result = Claim_adjudication.train_model(fileid)
   print(result)
   return result;
@app.route('/evaluate/<fileid>')
def evaluate_model(fileid):
   Claim_adjudication = claim_process_engine.claim_adjudication()
   prediction = Claim_adjudication.execute_model(fileid)
   #prediction = ','.join(str(e) for e in dtc1)
   return prediction
@app.route('/getmatrix')
def display_matrix():
   Claim_adjudication = claim_process_engine.claim_adjudication()
   ImageBytes = Claim_adjudication.Loadimage()
   return ImageBytes;

if __name__ == '__main__':
   app.run(debug=True)