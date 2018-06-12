from flask import Flask
import com.accenture.MLengine.claim_process_engine as claim_process_engine
import com.accenture.MLengine.database.dao as dao
app = Flask(__name__)


@app.route('/saveModel/<fileid>')
def save_model(fileid):
   Claim_adjudication = claim_process_engine.claim_adjudication()
   result = Claim_adjudication.train_model(fileid)
   return result;
@app.route('/evaluate/<fileid>')
def evaluate_model(fileid):
   Claim_adjudication = claim_process_engine.claim_adjudication()
   prediction,cnfmatrix,accScore = Claim_adjudication.execute_model(fileid)
   #prediction = ','.join(str(e) for e in dtc1)
   predictions_confmatrix = ','.join(str(e) for e in prediction) + "result" + accScore
   return predictions_confmatrix
@app.route('/getmatrix')
def display_matrix():
   Claim_adjudication = claim_process_engine.claim_adjudication()
   result=Claim_adjudication.Loadimage()
   print(result)
   return result;
@app.route('/saveModell')
def save_modell():
   Claim_adjudication = claim_process_engine.claim_adjudication()
   Dao = dao.daoClass()
   data = Dao.csv_LatestRetrival()
   fileid = data[0]
   result = Claim_adjudication.train_model(fileid)
   return result;

if __name__ == '__main__':
   app.run(port=5000,debug=True)