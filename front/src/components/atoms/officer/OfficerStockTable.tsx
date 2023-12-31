import React, { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { BiHomeAlt } from 'react-icons/bi';
import { customAxios } from '../../api/customAxios';

interface StockList {
  stockName: string;
  fromCompanyName: string;
  fromBranchName: string;
  stockDate: string;
  stockPrice: number;
  stockQuantity: number;
  stockUnit: string;
}

// 재고 현황 조회
const OfiicerStockTable = () => {
  const navigate = useNavigate();
  const [stockItems, setStockItems] = useState<StockList[]>([]);
  const [branchSeq, setBranchSeq] = useState(0);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // 토큰 들어오는거 기다리기
    const checkToken = () => {
      const storedValue = localStorage.getItem("recoil-persist");
      // const accessToken = storedValue && JSON.parse(storedValue)?.UserInfoState?.accessToken;
      const userInfo = storedValue && JSON.parse(storedValue)?.UserInfoState;
      const branchSeqFromStorage = userInfo ? userInfo.branchSeq : null;
      setBranchSeq(branchSeqFromStorage || 0);
      
      if (branchSeq !== 0) {          
        // 재고 정보 가져오기
        customAxios.get(`/stock/worker/dash/list/${branchSeq}`)
        .then((res) => {
          setStockItems(res.data.data.stockList);
          setIsLoading(false);
        })
        .catch((res) => {
          console.log(res);
        })
      } else {
        setTimeout(checkToken, 1000); // 1초마다 토큰 체크
      }
    };
    checkToken();
  }, [branchSeq]);

  // 가격 표기 형식 변환
  const formatPrice = (x: number) => {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  };

  // 날짜 표기 형식 변환
  const formatDateString = (dateString: string | undefined) => {
    if (!dateString) {
      return "";
    }
  
    const formattedDate = new Date(dateString);
    const year = formattedDate.getFullYear();
    const month = (formattedDate.getMonth() + 1).toString().padStart(2, "0");
    const day = formattedDate.getDate().toString().padStart(2, "0");
    const hours = formattedDate.getHours().toString().padStart(2, "0");
    const minutes = formattedDate.getMinutes().toString().padStart(2, "0");
  
    return `${year}년 ${month}월 ${day}일 ${hours}:${minutes}`;
  };


  return (
    <StockTableDiv>
      {isLoading ? (
        // 로딩중
        null
      ) : (
        <>
          <StyledTitle onClick={() => navigate('/stocklist')}>
            <BiHomeAlt color="#545A96" size={"30px"} style={{marginRight: "10px"}} />재고 현황
          </StyledTitle>
          <StyledTable>
            <thead>
              <tr style={{color: "black", backgroundColor: "#f2f2f2"}}>
                <th>품명</th>
                <th>입고업체</th>
                <th>입고단가</th>
                <th>입고수량</th>
                <th>입고일시</th>
              </tr>
            </thead>
            <tbody>
              {stockItems?.map((item, index) => (
                <tr key={index} style={{fontWeight: "lighter", backgroundColor: index % 2 === 1 ? "#f2f2f2" : ""}}>
                  <td>{item.stockName}</td>
                  <td>{item.fromCompanyName}</td>
                  <td>{formatPrice(item.stockPrice)} 원</td>
                  <td>{formatPrice(item.stockQuantity)} {item.stockUnit}</td>
                  <td>{formatDateString(item.stockDate)}</td>
                </tr>
              ))}
            </tbody>
          </StyledTable>
        </>
      )}
    </StockTableDiv>
  );
};

export default OfiicerStockTable;

const StockTableDiv = styled.div`
  width: 100%;
  height: 65%;
  /* border: 1px solid black; */
  box-shadow: 0px 5px 5px 0px rgba(0, 0, 0, 0.25);
  border-radius: 10px;
  margin: 0 20px 20px 20px;
  padding: 20px;
  background-color: white;
`

const StyledTitle = styled.div`
  display: flex;
  align-items: center;
  font-size: 20px;
  font-weight: bold;
  color: #545A96;
  cursor: pointer;
`

const StyledTable = styled.table`
  margin: 10px 0;
  width: 100%;
  border-collapse: collapse;
  color: #545A96;

  th, td {
    padding: 6px;
    text-align: center;
    font-weight: bold;
  }

  td {
    font-size: 14px;
  }

  th {
    font-size: 16px;
  }
`;