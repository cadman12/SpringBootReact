import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const OAuth2Callback = () => {

  const navigate = useNavigate();

  console.log('OAuth2Callback');

  useEffect(() => {
    const fetchJwtToken = async () => {
      fetch("http://localhost:8080/api/jwtcallback", {
        method: "POST",
        credentials: "include"  // 쿠키 포함
      })
      .then((res) => {
        console.log('res', res);
        const jwtToken = res.headers.get("Authorization");
        if (jwtToken) {
          console.log('token saved!', jwtToken);
          sessionStorage.setItem("jwtToken", jwtToken);
          navigate("/");
        } else {
          console.error("Authorization header is missing.");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
      });
    }
    fetchJwtToken();
  },[navigate]);

  return null;
};

export default OAuth2Callback;