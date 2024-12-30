import './App.css';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const ActionButton = ({ label, onClick }) => {
  return (
    <button className="btn" onClick={onClick}>
      {label}
    </button>
  );
};

const BoardTable = ({ data }) => {
  if (!data || data.length === 0) {
    return <div></div>;
  }

  return (
    <div className="table-responsive">
      <table className="table table-striped table-bordered table-hover">
        <thead className="thead-dark">
          <tr>
            <th>id</th>
            <th>title</th>
            <th>createDate</th>
            <th>username</th>
          </tr>
        </thead>
        <tbody>
          {data.map((row) => (
            <tr key={row.id}>
              <td>{row.id}</td>
              <td>{row.title}</td>
              <td>{row.createDate}</td>
              <td>{row.username}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

const MemberTable = ({ data }) => {
  if (!data || data.length === 0) {
    return <div></div>;
  }
  return (
    <div className="table-responsive">
      <table className="table table-striped table-bordered table-hover">
        <thead className="thead-dark">
          <tr>
            <th>username</th>
            <th>role</th>
            <th>enabled</th>
          </tr>
        </thead>
        <tbody>
          {data.map((row) => (
            <tr key={row.username}>
              <td>{row.username}</td>
              <td>{row.role}</td>
              <td>{(row.enabled)?'true':'false'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

const MainPage = () => {
  const [data1, setData1] = useState(null);
  const [data2, setData2] = useState(null);
  const [jwtToken, setJwtToken] = useState(null);
  const navigate = useNavigate();
  const hostAddr = `http://localhost:8080`;

  useEffect(() => {
    const token = sessionStorage.getItem("jwtToken");
    
    console.log('token', token);

    if (token) setJwtToken(token);
    else setJwtToken(null);
  }, []);

  const handleLogoutClick = () => {
    sessionStorage.removeItem("jwtToken");
    sessionStorage.removeItem("username");
    setJwtToken(null);
    setData1(null);
    setData2(null);
    navigate("/");
  };

  const handleLoginClick = () => {
    navigate("/login");
  };

  const fetchData = (type) => {
    let url = hostAddr;

    console.log('type', type);

    if (type === 1) {
      if (data1 != null) {
        setData1(null);
        return;
      }
      url += '/user/board';
    } else if (type === 2) {
      if (data2 != null) {
        setData2(null);
        return;
      }
      if (jwtToken == null) {
        navigate("/login");
        return;
      }
      url += '/manager/member';
    }

    fetch(url, {
      method: "GET",
      headers: {
        Authorization: jwtToken,
      },
    })
    .then((res) => {
      if (res.status !== 200) {
        navigate("/login");
        return;
      }
      return res.json();
    })
    .then((res) => {
      if (type === 1) {
        setData1(res);
      } else {
        setData2(res);
      }
    })
    .catch((error) => {
      console.error("Error:", error);
    });
  };

  const username = sessionStorage.getItem("username");
  const authContent = username ? (
    <ActionButton label="로그아웃" onClick={handleLogoutClick} />
  ) : (
    <ActionButton label="로그인" onClick={handleLoginClick} />
  );

  return (
    <div>
      <h2>메인 페이지</h2>
      <hr />
      <ActionButton
        label="자격증명없이 데이터 요청(Board)"
        onClick={() => fetchData(1)}
      />
      <BoardTable data={data1} />
      <br />
      <ActionButton
        label="자격증명 후 데이터 요청(Member)"
        onClick={() => fetchData(2)}
      />
      <MemberTable data={data2} />
      <br />
      <hr />
      {authContent}
    </div>
  );
};

export default MainPage;