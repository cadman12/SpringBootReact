import './App.css';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Login = (props) => {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const handleGoogleLogin = async () => {
    try {
      window.location.href = `http://localhost:8080/oauth2/authorization/google`;
    } catch (error) {
      console.error('Google OAuth2 login failed:', error);
    }
  };

  return (<div>
    <h2>로그인</h2>

    <div className="form">
      <p><input className="login" type="text" name="username" placeholder="아이디" onChange={event => {
        setUsername(event.target.value);
      }} /></p>
      <p><input className="login" type="password" name="password" placeholder="비밀번호" onChange={event => {
        setPassword(event.target.value);
      }} /></p>

      <p><input className="btn" type="submit" value="로그인" onClick={() => {
        const loginData = {
          username: username,
          password: password,
        };
        fetch("http://localhost:8080/login", {
          method: "post", // method :통신방법
          headers: {      // headers: API 응답에 대한 정보를 담음
            "content-type": "application/json",
          },
          body: JSON.stringify(loginData), //loginData 객체를 보냄
        }).then(res => {
          const token = res.headers.get('Authorization');
          if (token) {
            sessionStorage.setItem("username", username);
            sessionStorage.setItem("jwtToken", token);
            navigate("/");
          }
          else {
            alert("로그인 실패");
          }
        }).catch(error => {
          console.log('error', error);
        });
      }} /></p>
    </div>

    <p>계정이 없으신가요?{" "}
        <Link to="/signin">회원가입</Link>
    </p>
    <hr />
    <p>소셜 계정으로 로그인</p>
    <div>
        <button className="google-login-button" onClick={handleGoogleLogin}>
          <img
            src="https://developers.google.com/identity/images/btn_google_signin_dark_normal_web.png"
            alt=""
          />
        </button>
    </div>

  </div>)
};

export default Login;
