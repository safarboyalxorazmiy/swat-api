package apiserver

import (
	"encoding/base64"
	"encoding/json"
	"fmt"
	"image"
	"image/jpeg"
	"io"
	"net/http"
	"net/url"
	"os"
	"strconv"
	"strings"
	"time"
	"warehouse/internal/app/models"

	"github.com/gin-gonic/gin"
	"github.com/google/uuid"
)

// func checkError(message string, err error) {
// 	if err != nil {
// 		fmt.Println(message, err)
// 	}
// }

// func (s *Server) Test(c *gin.Context) {
// 	resp := models.Responce{}
// 	code, err := s.Store.Repo().Test()
// 	if err != nil {
// 		s.Logger.Error("Test: ", err)
// 		resp.Result = "error"
// 		resp.Err = err.Error()
// 		c.JSON(200, resp)
// 		c.Abort()
// 		return
// 	}

// 	// var data = [][]string{{code}}

// 	// file, err := os.Create("D:/gs_code/gscode.csv")
// 	// checkError("Cannot create file", err)
// 	// defer file.Close()

// 	// writer := csv.NewWriter(file)
// 	// defer writer.Flush()

// 	// for _, value := range data {
// 	// 	err := writer.Write(value)
// 	// 	checkError("Cannot write to file", err)
// 	// }

// 	ioutil.WriteFile("G:/gs_code/gscode.txt", []byte(code), 0644)
// 	resp.Result = "ok"
// 	c.JSON(200, resp)

// }
func (s *Server) ProductionLogistics(c *gin.Context) {
	resp := models.Responce{}
	lineIncome := c.GetInt("line")
	lineOutcome := c.GetInt("checkpoint")
	serial := c.GetString("serial")

	if err := s.Store.Repo().ProductionIncomeSerialsInput(lineIncome, serial); err != nil {
		s.Logger.Error("ProductionLogistics ProductionIncomeSerialsInput: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		c.Abort()
		return
	}

	err := s.Store.Repo().IncomeInProduction(lineIncome, lineOutcome, serial)
	if err != nil {
		s.Logger.Error("ProductionLogistics IncomeInProduction: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		c.Abort()
		return
	}

	resp.Result = "ok"
	c.JSON(200, resp)
}

func (s *Server) GetPlan(c *gin.Context) {
	type AllData struct {
		Count  int         `json:"count"`
		Models interface{} `json:"by_model"`
	}
	resp := models.Responce{}

	planCount, err := s.Store.Repo().PlanCountToday()
	if err != nil {
		s.Logger.Error("PlanCountToday: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}

	planModels, err := s.Store.Repo().PlanByModelToday()
	if err != nil {
		s.Logger.Error("PlanByModelToday: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}

	allData := AllData{}

	allData.Count = planCount
	allData.Models = planModels
	resp.Data = allData
	resp.Result = "ok"

	c.JSON(200, resp)
}

func (s *Server) GetLast(c *gin.Context) {
	resp := models.Responce{}
	temp, _ := c.Get("line")
	line := temp.(int)

	data, err := s.Store.Repo().GetLast(line)
	if err != nil {
		s.Logger.Error("GetLast: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetStatus(c *gin.Context) {
	resp := models.Responce{}
	temp, _ := c.Get("line")
	line := temp.(int)

	data, err := s.Store.Repo().GetStatus(line)
	if err != nil {
		s.Logger.Error("GetLast: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetToday(c *gin.Context) {
	resp := models.Responce{}
	line := c.GetInt("line")
	// line := temp.(int)

	today, err := s.Store.Repo().GetToday(line)
	if err != nil {
		s.Logger.Error("GetLast: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}

	s.Logger.Info(line, today)
	c.JSON(200, today)
}

func (s *Server) GetTodayModels(c *gin.Context) {
	resp := models.Responce{}
	temp, _ := c.Get("line")
	line := temp.(int)

	data, err := s.Store.Repo().GetTodayModels(line)
	if err != nil {
		s.Logger.Error("GetTodayModels: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetSectorBalance(c *gin.Context) {
	resp := models.Responce{}
	line := c.GetInt("line")

	data, err := s.Store.Repo().GetSectorBalance(line)
	if err != nil {
		s.Logger.Error("GetSectorBalance: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetSectorBalanceGP(c *gin.Context) {
	resp := models.Responce{}
	line := c.GetInt("line")

	data, err := s.Store.Repo().GetSectorBalanceGP(line)
	if err != nil {
		s.Logger.Error("GetSectorBalance: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	resp.Result = "ok"
	resp.Data = data
	c.JSON(200, resp)
}

func (s *Server) SectorBalanceUpdate(c *gin.Context) {
	resp := models.Responce{}
	temp, _ := c.Get("line")
	temp2, _ := c.Get("component_id")
	temp3, _ := c.Get("quantity")
	line := temp.(int)
	quantity := temp3.(float64)
	component_id := temp2.(int)

	err := s.Store.Repo().SectorBalanceUpdate(line, component_id, quantity)
	if err != nil {
		s.Logger.Error("GetSectorBalanceUpdate: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	resp.Result = "ok"
	c.JSON(200, resp)
}

func (s *Server) GetPackingLast(c *gin.Context) {
	resp := models.Responce{}
	s.Logger.Info("packing last")
	data, err := s.Store.Repo().GetPackingLast()
	if err != nil {
		s.Logger.Error("GetPackingLast: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetPackingToday(c *gin.Context) {
	resp := models.Responce{}
	data, err := s.Store.Repo().GetPackingToday()
	if err != nil {
		s.Logger.Error("GetPackingToday: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetPackingTodaySerial(c *gin.Context) {
	resp := models.Responce{}
	data, err := s.Store.Repo().GetPackingTodaySerial()
	if err != nil {
		s.Logger.Error("GetPackingTodaySerial: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetPackingTodayModels(c *gin.Context) {
	resp := models.Responce{}
	data, err := s.Store.Repo().GetPackingTodayModels()
	if err != nil {
		s.Logger.Error("GetPackingTodayModels: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetLines(c *gin.Context) {
	resp := models.Responce{}
	data, err := s.Store.Repo().GetLines()
	if err != nil {
		s.Logger.Error("GetLines: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetDefectsTypes(c *gin.Context) {
	resp := models.Responce{}
	data, err := s.Store.Repo().GetDefectsTypes()
	if err != nil {
		s.Logger.Error("GetDefectsTypes: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) DeleteDefectsTypes(c *gin.Context) {
	resp := models.Responce{}

	temp, _ := c.Get("id")
	id := temp.(int)

	err := s.Store.Repo().DeleteDefectsTypes(id)
	if err != nil {
		s.Logger.Error("GetDefectsTypes: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	resp.Result = "ok"
	c.JSON(200, resp)
}

func (s *Server) AddDefectsTypes(c *gin.Context) {
	resp := models.Responce{}
	temp, _ := c.Get("line")
	temp2, _ := c.Get("name")
	line := temp.(int)
	name := temp2.(string)

	err := s.Store.Repo().AddDefectsTypes(line, name)
	if err != nil {
		s.Logger.Error("AddDefectsTypes: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	resp.Result = "ok"
	c.JSON(200, resp)
}

func (s *Server) AddDefects(c *gin.Context) {
	resp := models.Responce{}
	serial := c.GetString("serial")
	checkpoint := c.GetInt("checkpoint_id")
	name := c.GetString("name")
	defect := c.GetInt("defect_id")
	image2 := c.GetString("image")

	reader := base64.NewDecoder(base64.StdEncoding, strings.NewReader(image2))
	m, formatString, err := image.Decode(reader)
	if err != nil {
		s.Logger.Error("AddDefects decode image: ", err)
		resp.Result = "error"
		resp.Err = "Error decode"
		c.JSON(200, resp)
		return
	}
	bounds := m.Bounds()
	s.Logger.Info("base64toJpg: ", bounds, formatString)

	//Encode from image format to writer
	fileName := uuid.New().String() + ".jpg"
	pngFilename := `g:\premier\server_V2\global\media\` + fileName //adress in server
	// pngFilename := `D:\premier\v2\Global\media\` + fileName
	f, err := os.OpenFile(pngFilename, os.O_WRONLY|os.O_CREATE, 0777)

	if err != nil {
		s.Logger.Error("AddDefects write image: ", err)
		resp.Result = "error"
		resp.Err = "Error write"
		c.JSON(200, resp)
		return
	}
	defer f.Close()
	err = jpeg.Encode(f, m, &jpeg.Options{Quality: 75})
	if err != nil {
		s.Logger.Error("AddDefects encode image: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	fmt.Println("Jpg file", pngFilename, "created")

	err = s.Store.Repo().AddDefects(serial, name, fileName, checkpoint, defect)
	if err != nil {
		s.Logger.Error("AddDefects: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}

	count, err := s.Store.Repo().CheckCountRemont(serial)

	if err != nil {
		s.Logger.Error("CheckCountRemont: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}

	if count >= 3 {
		err := s.Store.Repo().BlockProduct(serial)
		if err != nil {
			s.Logger.Error("BlockProduct: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}
	}

	resp.Result = "ok"
	c.JSON(200, resp)
}

func (s *Server) Last3Defects(c *gin.Context) {
	resp := models.Responce{}

	data, err := s.Store.Repo().Last3Defects()
	if err != nil {
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
	}

	resp.Result = "ok"
	resp.Data = data

	c.JSON(200, resp)
}

func (s *Server) GetByDateSerial(c *gin.Context) {
	resp := models.Responce{}
	temp, _ := c.Get("date1")
	temp2, _ := c.Get("date2")
	date1 := temp.(string)
	date2 := temp2.(string)

	data, err := s.Store.Repo().GetByDateSerial(date1, date2)
	if err != nil {
		s.Logger.Error("GetByDateSerial: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetByHoursSerial(c *gin.Context) {
	resp := models.Responce{}
	temp, _ := c.Get("date1")
	temp2, _ := c.Get("date2")
	date1 := temp.(string)
	date2 := temp2.(string)

	data, err := s.Store.Repo().GetByDateSerial(date1, date2)
	if err != nil {
		s.Logger.Error("GetByDateSerial: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetCountByDate(c *gin.Context) {
	resp := models.Responce{}
	date1 := c.GetString("date1")
	date2 := c.GetString("date2")
	line := c.GetInt("line")

	data, err := s.Store.Repo().GetCountByDate(date1, date2, line)
	if err != nil {
		s.Logger.Error("GetByDate: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetCountByHours(c *gin.Context) {
	resp := models.Responce{}
	date1 := c.GetString("date1")
	date2 := c.GetString("date2")
	line := c.GetInt("line")

	data, err := s.Store.Repo().GetCountByHours(date1, date2, line)
	if err != nil {
		s.Logger.Error("GetByDate: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}
func (s *Server) GetByDateModels(c *gin.Context) {
	resp := models.Responce{}
	temp, _ := c.Get("date1")
	temp2, _ := c.Get("date2")
	temp3, _ := c.Get("line")
	date1 := temp.(string)
	date2 := temp2.(string)
	line := temp3.(int)

	data, err := s.Store.Repo().GetByDateModels(date1, date2, line)
	if err != nil {
		s.Logger.Error("GetByDateModels: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetByHoursModels(c *gin.Context) {
	resp := models.Responce{}
	date1 := c.GetString("date1")
	date2 := c.GetString("date2")
	line := c.GetInt("line")

	data, err := s.Store.Repo().GetByHoursModels(date1, date2, line)
	if err != nil {
		s.Logger.Error("GetByHoursModels: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetRemont(c *gin.Context) {
	resp := models.Responce{}
	data, err := s.Store.Repo().GetRemont()
	if err != nil {
		s.Logger.Error("GetRemont: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetRepairedCount(c *gin.Context) {
	resp := models.Responce{}

	count, err := s.Store.Repo().GetCountRepaired()
	if err != nil {
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	resp.Result = "ok"
	resp.Data = count
	c.JSON(200, resp)

}

func (s *Server) GetRemontToday(c *gin.Context) {
	resp := models.Responce{}
	data, err := s.Store.Repo().GetRemontToday()
	if err != nil {
		s.Logger.Error("GetRemontToday: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) GetRemontByDate(c *gin.Context) {
	resp := models.Responce{}
	date1 := c.GetString("date1")
	date2 := c.GetString("date2")

	data, err := s.Store.Repo().GetRemontByDate(date1, date2)
	if err != nil {
		s.Logger.Error("GetByDate: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}

	c.JSON(200, data)
}

func (s *Server) UpdateRemont(c *gin.Context) {
	resp := models.Responce{}
	temp, _ := c.Get("name")
	temp2, _ := c.Get("id")
	name := temp.(string)
	id := temp2.(int)

	serial, err := s.Store.Repo().GetSerialFromRemontId(id)
	if err != nil {
		s.Logger.Error("GetSerialFromRemontId: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}

	checkBlocked := s.Store.Repo().CheckBlockedProduct(serial)

	if checkBlocked > 0 {
		s.Logger.Error("Try Update Blocked Product: ", serial)
		resp.Result = "error"
		resp.Err = "Blocked Product"
		c.JSON(200, resp)
		return

	}

	err = s.Store.Repo().UpdateRemont(name, id)
	if err != nil {
		s.Logger.Error("GetByDate: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	resp.Result = "ok"
	c.JSON(200, resp)
}

func (s *Server) SerialInput(c *gin.Context) {
	resp := models.Responce{}
	serial := c.GetString("serial")
	line := c.GetInt("line")

	err := s.Store.Repo().SerialInput(line, serial)
	if err != nil {
		s.Logger.Error("SerialInput: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}

	resp.Result = "ok"
	c.JSON(200, resp)
}

func GetPhoto(serial string, c *gin.Context) (string, error) {

	fmt.Println("get Photo")
	// s.lo.Info("Check laboratory")
	// 	response, err := http.PostForm("http://192.168.5.193:5555/getphoto", url.Values{
	response, err := http.PostForm("http://192.168.5.85:5555/getphoto", url.Values{
		"serial": {serial}})
	if err != nil {
		return "", err

	}
	defer response.Body.Close()
	body, err := io.ReadAll(response.Body)
	if err != nil {
		return "", err
	}

	currentYear := time.Now().Year()
	currentMonth := time.Now().Format("1")
	currentTime := time.Now()
	currentDay := currentTime.Day()

	fmt.Println("g:/premier/server_V2/global/media/" + strconv.Itoa(currentYear) + "/" + currentMonth + "/" + strconv.Itoa(currentDay) + "/" + serial + ".jpg")
	err = os.MkdirAll("g:/premier/server_V2/global/media/"+strconv.Itoa(currentYear)+"/"+currentMonth+"/"+strconv.Itoa(currentDay)+"/", os.ModePerm)
	if err != nil {
		fmt.Println("Error creating directory:", err)
		return "", err
	}

	locationString := strconv.Itoa(currentYear) + "/" + currentMonth + "/" + strconv.Itoa(currentDay) + "/" + serial + ".jpg"
	// err = os.WriteFile(`D:\temp\`+serial+`.jpg`, []byte(body), 0644)
	err = os.WriteFile("g:/premier/server_V2/global/media/"+strconv.Itoa(currentYear)+"/"+currentMonth+"/"+strconv.Itoa(currentDay)+"/"+serial+".jpg", []byte(body), 0644)
	if err != nil {
		return "", err
	}
	// db ga saqlashim kerak endi shunaqaya?

	return locationString, nil
}

func CopyFile(src, dst string) (err error) {
	sfi, err := os.Stat(src)
	if err != nil {
		return
	}
	if !sfi.Mode().IsRegular() {
		// cannot copy non-regular files (e.g., directories,
		// symlinks, devices, etc.)
		return fmt.Errorf("CopyFile: non-regular source file %s (%q)", sfi.Name(), sfi.Mode().String())
	}
	dfi, err := os.Stat(dst)
	if err != nil {
		if !os.IsNotExist(err) {
			return
		}
	} else {
		if !(dfi.Mode().IsRegular()) {
			return fmt.Errorf("CopyFile: non-regular destination file %s (%q)", dfi.Name(), dfi.Mode().String())
		}
		if os.SameFile(sfi, dfi) {
			return
		}
	}
	if err = os.Link(src, dst); err == nil {
		return
	}
	err = copyFileContents(src, dst)
	return
}

// copyFileContents copies the contents of the file named src to the file named
// by dst. The file will be created if it does not already exist. If the
// destination file exists, all it's contents will be replaced by the contents
// of the source file.
func copyFileContents(src, dst string) (err error) {
	// checkLan()
	in, err := os.Open(src)
	if err != nil {
		return
	}
	defer in.Close()
	out, err := os.Create(dst)
	if err != nil {
		return
	}
	defer func() {
		cerr := out.Close()
		if err == nil {
			err = cerr
		}
	}()
	if _, err = io.Copy(out, in); err != nil {
		return
	}
	err = out.Sync()
	return
}

func (s *Server) PackingSerialInput(c *gin.Context) {
	resp := models.Responce{}
	serial := c.GetString("serial")
	packing := c.GetString("packing")
	retry := c.GetBool("retry")

	// type Result struct {
	// 	Result string `json:"result"`
	// 	Err    string `json:"error"`
	// }

	location, err := GetPhoto(serial, c)
	if err != nil {
		s.Logger.Error("GetPhoto: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}

	// err := CopyFile("\\\\192.168.5.85\\camera\\"+serial+`.jpg`, `g:\premier\server_V2\global\media\`+serial+`.jpg`)

	// if err != nil {
	// 	fmt.Printf("CopyFile failed %q\n", err)
	// } else {
	// 	fmt.Printf("CopyFile succeeded\n")
	// }

	check_lab, err := s.Store.Repo().CheckLaboratory(serial)
	if err != nil {
		s.Logger.Error(err)
		resp.Result = "error"
		resp.Err = err
		c.JSON(200, resp)
	}

	fromLab := string(check_lab)
	data := models.Laboratory{}
	json.Unmarshal([]byte(fromLab), &data)

	if data.Result != "Good" {
		s.Logger.Error(err)
		resp.Result = "error"
		resp.Err = "laboratoriyada muammo: " + data.Result
		c.JSON(200, resp)
		return
	}

	s.Store.Repo().LabInfoInput(data)

	// s.Logger.Info("Route: PackingSerialInput, retry: ", retry)
	// packing := temp2.(string)

	// if packing == serial || packing == "" {
	// 	s.Logger.Error("SerialInput: ", "serial: ", serial, "packing: ", packing)
	// 	resp.Result = "error"
	// 	resp.Err = "Serial xato"
	// 	c.JSON(200, resp)
	// 	return
	// }

	// chechRemontStatus := s.Store.Repo().CheckRemontStatusProduct(serial)

	// if chechRemontStatus > 0 {
	// 	s.Logger.Error("CheckRemontStatusProduct: ", serial)
	// 	resp.Result = "error"
	// 	resp.Err = "Remont tugallanmagan"
	// 	c.JSON(200, resp)
	// 	return
	// }

	err = s.Store.Repo().PackingSerialInput(serial, packing, retry, location) //, packing)
	if err != nil {
		s.Logger.Error("SerialInput: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	resp.Result = "ok"
	c.JSON(200, resp)
}

func (s *Server) CheckRemont(c *gin.Context) {
	resp := models.Responce{}
	serial := c.GetString("serial")

	data, err := s.Store.Repo().CheckRemont(serial)
	fmt.Println("data: ", data)
	if err != nil {
		s.Logger.Error("CheckRemont: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		resp.Data = data
		c.JSON(200, resp)
		return
	}
	resp.Result = "ok"
	resp.Data = data
	c.JSON(200, resp)
}

func (s *Server) GetInfoBySerial(c *gin.Context) {
	resp := models.Responce{}
	temp, _ := c.Get("serial")
	serial := temp.(string)

	data, err := s.Store.Repo().GetInfoBySerial(serial)
	if err != nil {
		s.Logger.Error("GetInfoBySerial: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	resp.Result = "ok"

	resp.Data = data

	c.JSON(200, resp)
}

func (s *Server) GalileoInput(c *gin.Context) {

	req := models.Galileo{}
	resp := models.Responce{}

	if err := c.ShouldBind(&req); err != nil {
		s.Logger.Error("GalileoInput parse error: ", err)
		resp.Result = "error"
		resp.Err = err
		c.JSON(401, resp)
		c.Abort()
		return
	}

	err := s.Store.Repo().GalileoInput(&req)
	if err != nil {
		s.Logger.Error("SerialInput: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	resp.Result = "ok"
	c.JSON(200, resp)
}

func (s *Server) GalileoTodayModels(c *gin.Context) {
	resp := models.Responce{}

	data, err := s.Store.Repo().GalileoTodayModels()
	if err != nil {
		s.Logger.Error("SerialInput: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}
	c.JSON(200, data)
}

func (s *Server) MetallSerial(c *gin.Context) {
	resp := models.Responce{}
	id := c.GetInt("id")

	// id := temp.(int)

	err := s.Store.Repo().Metall_Serial(id)
	s.Logger.Info(id)
	if err != nil {
		s.Logger.Error("Metall_Serial: ", err)
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
		return
	}

	resp.Result = "ok"
	c.JSON(200, resp)
}

func (s *Server) VakumSerial(c *gin.Context) {

	resp := models.Responce{}
	id := c.GetInt("id")

	switch id {
	case 1:
		used_component := 359
		gp_component_1 := 335
		gp_component_2 := 336

		err := s.Store.Repo().VakumSerialPrint(id)
		if err != nil {
			s.Logger.Error("VakumSerialPrint: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}
		err = s.Store.Repo().VakumIscreaseComponent(used_component)
		if err != nil {
			s.Logger.Error("VakumIscreaseComponent: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}

		err = s.Store.Repo().VakumAddGpComponent(gp_component_1)
		if err != nil {
			s.Logger.Error("VakumAddGpComponent: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}
		err = s.Store.Repo().VakumAddGpComponent(gp_component_2)
		if err != nil {
			s.Logger.Error("VakumAddGpComponent: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}

	case 3:
		used_component := 358
		gp_component_1 := 337
		gp_component_2 := 338

		err := s.Store.Repo().VakumSerialPrint(id)
		if err != nil {
			s.Logger.Error("VakumSerialPrint: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}
		err = s.Store.Repo().VakumIscreaseComponent(used_component)
		if err != nil {
			s.Logger.Error("VakumIscreaseComponent: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}

		err = s.Store.Repo().VakumAddGpComponent(gp_component_1)
		if err != nil {
			s.Logger.Error("VakumAddGpComponent: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}
		err = s.Store.Repo().VakumAddGpComponent(gp_component_2)
		if err != nil {
			s.Logger.Error("VakumAddGpComponent: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}
	case 6:
		used_component := 333
		gp_component_1 := 447

		err := s.Store.Repo().VakumSerialPrint(id)
		if err != nil {
			s.Logger.Error("VakumSerialPrint: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}
		err = s.Store.Repo().VakumIscreaseComponent(used_component)
		if err != nil {
			s.Logger.Error("VakumIscreaseComponent: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}

		err = s.Store.Repo().VakumAddGpComponent(gp_component_1)
		if err != nil {
			s.Logger.Error("VakumAddGpComponent: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}
	case 7:
		used_component := 334
		gp_component_1 := 448

		err := s.Store.Repo().VakumSerialPrint(id)
		if err != nil {
			s.Logger.Error("VakumSerialPrint: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}
		err = s.Store.Repo().VakumIscreaseComponent(used_component)
		if err != nil {
			s.Logger.Error("VakumIscreaseComponent: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}

		err = s.Store.Repo().VakumAddGpComponent(gp_component_1)
		if err != nil {
			s.Logger.Error("VakumAddGpComponent: ", err)
			resp.Result = "error"
			resp.Err = err.Error()
			c.JSON(200, resp)
			return
		}

	}

	resp.Result = "ok"
	c.JSON(200, resp)
}

func (s *Server) GetBlockedProducts(c *gin.Context) {
	resp := models.Responce{}

	data, err := s.Store.Repo().GetBlockedProducts()
	if err != nil {
		resp.Result = "error"
		resp.Err = err.Error()
		c.JSON(200, resp)
	}

	resp.Result = "ok"
	resp.Data = data

	c.JSON(200, resp)
}

// func Float64frombytes(bytes []byte) float32 {
// 	bits := binary.LittleEndian.Uint32(bytes)
// 	float := math.Float32frombits(bits)
// 	return float
// }

// func (s *Server) GalileoTCP(c *gin.Context) {
// 	resp := models.Responce{}
// 	data := c.GetString("data")

// 	s.Logger.Info("data: ", data)

// 	message := []byte(data)

// 	s.Logger.Info("message: ", message)

// 	fmt.Print("Message type:", reflect.TypeOf(message))
// 	fmt.Println("mesasge: ", message)
// 	fmt.Println("mesasge String: ", string(message))
// 	if len(message) > 100 {
// 		serial := string(message[14:40])
// 		opCode := string(message[85:89])
// 		month := int(message[55])
// 		day := int(message[57])
// 		hour := int(message[59])
// 		minute := int(message[61])
// 		programQuantity := Float64frombytes(message[119 : 119+9])
// 		realQuantity := Float64frombytes(message[123 : 123+9])

// 		fmt.Println("serial: ", serial)
// 		fmt.Println("opCode: ", opCode)
// 		fmt.Println("month: ", month)
// 		fmt.Println("day: ", day)
// 		fmt.Println("hour: ", hour)
// 		fmt.Println("minute: ", minute)
// 		fmt.Println("programQuantity: ", programQuantity)
// 		fmt.Println("realQuantity: ", realQuantity)

// 		for i := 0; i < len(message)-10; i++ {

// 			temp := Float64frombytes(message[i : i+9])
// 			fmt.Println(i, ": ", temp)
// 			// buf := bytes.NewBuffer(message[i:])
// 			// tt, _ := binary.ReadUvarint(buf)

// 			// // tt := uint32(message[i+2])
// 			// fmt.Println(i, ": ", tt)

// 		}

// 	}

// 	resp.Result = "ok"

// 	c.JSON(200, resp)
// }
