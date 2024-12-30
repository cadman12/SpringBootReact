import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './Login';
import Signin from './Signin';
import MainPage from './MainPage';
import OAuth2Callback from './OAuth2Callback';

const App = () => {

  return (
    <Router>
      <div className="background">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/signin" element={<Signin />} />
          <Route path="/" element={<MainPage />} />
          <Route path="/oauth2/callback" element={<OAuth2Callback />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;