package apiserver

import (
	"io"
	"os"
	"warehouse/internal/app/store/sqlstore"

	"github.com/gin-gonic/gin"
	log "github.com/sirupsen/logrus"
)

var Secret_key = []byte("Some123SecretKeyPremier1")

type Server struct {
	Router *gin.Engine
	Logger *log.Logger
	Store  sqlstore.Store
}

func newServer(store sqlstore.Store) *Server {
	s := &Server{
		Router: gin.New(),
		Logger: log.New(),
		Store:  store,
	}
	f, err := os.OpenFile("logger.log", os.O_RDWR|os.O_CREATE|os.O_APPEND, 0666)
	if err != nil {
		log.Fatalf("error opening file: %v", err)
	}
	wrt := io.MultiWriter(os.Stdout, f)
	// cors.AllowAll()
	// cors.Default()
	// s.Router.Use()

	s.Logger.SetOutput(wrt)
	s.Logger.SetFormatter(&log.JSONFormatter{})
	s.configureRouter()
	return s
}

func CORSMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		c.Writer.Header().Set("Access-Control-Allow-Origin", "*")
		c.Writer.Header().Set("Access-Control-Allow-Credentials", "true")
		c.Writer.Header().Set("Access-Control-Allow-Headers", "Content-Type, Content-Length, Accept-Encoding, X-CSRF-Token, Authorization, accept, origin, Cache-Control, X-Requested-With")
		c.Writer.Header().Set("Access-Control-Allow-Methods", "POST, OPTIONS, GET, PUT")

		if c.Request.Method == "OPTIONS" {
			c.AbortWithStatus(204)
			return
		}

		c.Next()
	}
}

func (s *Server) configureRouter() {
	s.Router.SetTrustedProxies([]string{"localhost"})
	s.Router.Use(CORSMiddleware())

	s.Router.POST("users/login", s.Login)             // {"email": string, "password": string}
	s.Router.POST("ware/outcome/file", s.OutcomeFile) //only excel file input
	s.Router.POST("ware/gscode/file", s.GsCodeFile)

	ware := s.Router.Group("/ware") //route for warehouse control
	ware.Use(s.WareCheckRole())
	{
		ware.POST("/components", s.GetAllComponents)                                     // {"token": string}
		ware.POST("/components/outcome", s.GetAllComponentsOutCome)                      // {"token": string}
		ware.POST("/components/outcome/byquantity", s.GetAllComponentsOutComeByQuantity) // {"token": string}
		ware.POST("/components/gp", s.GetGPCompontents)                                  // {"token": string}
		ware.POST("/components/gp/add", s.GPCompontentsAdd)                              // {"token": string, "checkpoint_id":int, "component_id":int, "model_id":int}
		ware.POST("/components/gp/added", s.GPCompontentsAdded)                          // {"token": string}
		ware.POST("/components/gp/remove", s.GPCompontentsRemove)                        // {"token": string}
		ware.POST("/component", s.GetCompoment)                                          // {"id": int, "token": string}
		ware.POST("/component/update", s.UpdateCompoment)                                // {"code":string, "name":string, "checkpoint_id":int, "unit":string, "photo":string, "specs":string, "type_id":int, "weight":float64, "id":int, "token": string}
		ware.POST("/component/add", s.AddComponent)                                      // {"code":string, "name":string, "checkpoint_id":int, "unit":string, "photo":string, "specs":string, "type_id":int, "weight":float64, "token": string}
		ware.POST("/component/delete", s.DeleteCompoment)                                // {"id":int, "token": string}
		ware.POST("/checkpoints", s.GetAllCheckpoints)                                   // {"token": string}
		ware.POST("/checkpoint/delete", s.DeleteCheckpoint)                              // {"id":int, "token": string}
		ware.POST("/checkpoint/add", s.AddCheckpoint)                                    // {"name":string, "photo":string, "token": string}
		ware.POST("/checkpoint/update", s.UpdateCheckpoint)                              // {"name":string, "photo":string, "id":int, "token": string}
		ware.POST("/income", s.Income)                                                   // {"component_id":int, "quantity":int, "token": string}
		ware.POST("/metalhgghhh", s.Income)                                              // {"component_id":int, "quantity":int, "token": string}
		ware.POST("/income/report", s.IncomeReport)                                      // {"date1":string, "date2":string, "token": string}
		ware.POST("/types", s.Types)                                                     // {"token": string}
		ware.POST("/models", s.Models)                                                   // {"token": string}
		ware.POST("/model", s.Model)                                                     // {"id":int, "token": string}
		ware.POST("/outcome/model/check", s.OutcomeModelCheck)                           // {"id":int, "token": string}
		ware.POST("/outcome/model/submit", s.OutcomeModelSubmit)                         // {"model_id":int, "quantity":float64, "token": string}
		ware.POST("/outcome/component/check", s.OutcomeComponentCheck)                   // {"component_id":int, "quantity":float64, "token": string}
		ware.POST("/outcome/component/submit", s.OutcomeComponentSubmit)                 // {"component_id":int, "checkpoint_id":int, "quantity":float64, "token": string}
		ware.POST("/outcome/report", s.OutcomeReport)                                    // {"date1":string, "date2":string, "token": string}
		ware.POST("/model/update", s.InsertUpdateModel)                                  // {"specs": string, "id":int, "code"string, "comment":string, "name":string, "token": string} specs=>assembly name
		ware.POST("/bom/component", s.BomComponentInfo)                                  // {"id":int, "token": string}
		ware.POST("/bom/component/add", s.BomComponentAdd)                               // {"id":int, "token": string}
		ware.POST("/bom/component/delete", s.BomComponentDelete)                         // {"model_id":int, "component_id":int, "token": string}
		ware.POST("/bom/component/update", s.BomUpdate)                                  // {"model_id":int, "component_id":int, "quantity": float64, "token": string}
		ware.POST("/production/sector/balance", s.GetSectorBalance)                      // {"line":int, "token": string}
		ware.POST("/production/sector/balance/update", s.SectorBalanceUpdate)            // {"line":int, "component_id": int, "quantity": float64, "token": string}
		ware.POST("/gscode/get", s.GetKeys)                                              // {"token": string}
		ware.POST("/akt/input", s.AktInput)                                              // {"token": string, "component_id": int, "comment": string, "quantity": float64, "checkpoint_id": int}
		ware.POST("/akt/input/ware", s.AktInputWare)                                     // {"token": string, "component_id": int, "comment": string, "quantity": float64, "checkpoint_id": int}
		ware.POST("/akt/report", s.AktReport)                                            // {"date1":string, "date2":string, "token": string}
		ware.POST("/cell/getempty", s.CellGetEmpty)                                      // {"lot_id":int, "component_id":int, "token": string}
		ware.POST("/cell/getall", s.CellGetAll)                                          // {"token": string}
		ware.POST("/cell/addcomponent", s.CellAddComponent)                              // {"lot_id":int, "component_id":int, "cell_id": int, "quantity": float64, "token": string}
		ware.POST("/cell/getbycomponent", s.CellGetByComponent)                          // {"lot_id":int, "component_id":int, "cell_id": int, "quantity": float64, "token": string}
		ware.POST("/cell/getbycomponent/all", s.CellGetByComponentAll)                   // {"lot_id":int, "component_id":int, "cell_id": int, "quantity": float64, "token": string}
		ware.POST("/plan/getbymonth", s.GetByMonthPlan)                                  // {"lot_id":int, "component_id":int, "cell_id": int, "quantity": float64, "token": string}
		ware.POST("/plan/getcurrent", s.GetCurrentPlan)                                  // {"lot_id":int, "component_id":int, "cell_id": int, "quantity": float64, "token": string}
		ware.POST("/plan/update", s.PlanUpdate)                                          // {"lot_id":int, "component_id":int, "cell_id": int, "quantity": float64, "token": string}
	}

	global := s.Router.Group("/api") //Route for global use
	global.Use(s.CheckRole())
	{
		global.POST("/production/last", s.GetLast)                                  // {"line": int, "token": string}
		global.POST("/production/status", s.GetStatus)                              // {"line": int, "token": string}
		global.POST("/production/today", s.GetToday)                                // {"line": int, "token": string}
		global.POST("/production/today/models", s.GetTodayModels)                   // {"line": int, "token": string}
		global.POST("/production/sector/balance", s.GetSectorBalance)               // {"line": int, "token": string}
		global.POST("/production/packing/last", s.GetPackingLast)                   // {"token": string}
		global.POST("/production/packing/today", s.GetPackingToday)                 // {"token": string}
		global.POST("/production/packing/today/serial", s.GetPackingTodaySerial)    // {"token": string}
		global.POST("/production/packing/today/models", s.GetPackingTodayModels)    // {"token": string}
		global.POST("/production/lines", s.GetLines)                                // {"token": string}
		global.POST("/production/defects/types", s.GetDefectsTypes)                 // {"token": string}
		global.POST("/production/defects/types/delete", s.DeleteDefectsTypes)       // {"id": int,"token": string}
		global.POST("/production/defects/types/add", s.AddDefectsTypes)             // {"id": int,"token": string}
		global.POST("/production/defects/add", s.AddDefects)                        // {"serial": string, "checkpoint_id": int, "defect_id": int, "token": string}
		global.POST("/production/defects/last", s.Last3Defects)                     // {"serial": string, "checkpoint_id": int, "defect_id": int, "token": string}
		global.POST("/production/report/bydate/models/serial", s.GetByDateSerial)   // {"date1": string, "date2": string, "line": int, "token": string}
		global.POST("/production/report/byhours/models/serial", s.GetByHoursSerial) // {"date1": string, "date2": string, "line": int, "token": string}
		global.POST("/production/report/bydate", s.GetCountByDate)                  // {"date1": string, "date2": string, "line": int, "token": string}
		global.POST("/production/report/byhours", s.GetCountByHours)                // {"date1": string, "date2": string, "line": int, "token": string}
		global.POST("/production/report/bydate/models", s.GetByDateModels)          // {"date1": string, "date2": string, "line": int, "token": string}
		global.POST("/production/report/byhours/models", s.GetByHoursModels)        // {"date1": string, "date2": string, "line": int, "token": string}
		global.POST("/production/report/remont", s.GetRemont)                       // {"token": string}
		global.POST("/production/report/remont/today", s.GetRemontToday)            // {"token": string}
		global.POST("/production/report/remont/bydate", s.GetRemontByDate)          // {"date1": string, "date2": string, "token": string}
		global.POST("/production/report/remont/update", s.UpdateRemont)             // {"name": string, "id": int, "token": string} id-> defect id
		global.POST("/production/report/remont/repairedcount", s.GetRepairedCount)  // {"name": string, "id": int, "token": string} id-> defect id
		global.POST("/production/serial/info", s.GetInfoBySerial)                   // {"serial": string, "token": string}
		global.POST("/production/galileo/todaymodels", s.GalileoTodayModels)        // {"token": string}
		global.POST("/users/register", s.Create)                                    // register user                                  // {"email":string, "password":string,"token": string}
		global.POST("/production/today/statistics", s.TodayStatistics)              // {"token": string}
		global.POST("/production/plan/today", s.GetPlan)                            // {"token": string}
		global.POST("/ware/components/outcome", s.GetAllComponentsOutCome)          // {"token": string}
		global.POST("/ware/blocked/getList", s.GetBlockedProducts)                  // {"token": string}
		global.POST("/plan/getcurrent", s.GetCurrentPlan)
		global.POST("/plan/today", s.GetPlanToday)

	}

	production := s.Router.Group("/production") //ONLY FOR PRODUCTION(factory) without check token and role
	production.Use(s.NoCheckRole())
	{
		production.POST("/last", s.GetLast)                                 // {"line": int}
		production.POST("/status", s.GetStatus)                             // {"line": int}
		production.POST("/today", s.GetToday)                               // {"line": int}
		production.POST("/today/models", s.GetTodayModels)                  // {"line": int}
		production.POST("/sector/balance", s.GetSectorBalance)              // {"line": int}
		production.POST("/sector/balance/gp", s.GetSectorBalanceGP)         // {"line": int}
		production.POST("/packing/last", s.GetPackingLast)                  // {}
		production.POST("/packing/today", s.GetPackingToday)                // {}
		production.POST("/packing/today/serial", s.GetPackingTodaySerial)   // {}
		production.POST("/packing/today/models", s.GetPackingTodayModels)   // {}
		production.POST("/packing/serial/input", s.PackingSerialInput)      // {"serial":string, "packing":string}
		production.POST("/lines", s.GetLines)                               // {}
		production.POST("/defects/types", s.GetDefectsTypes)                // {}
		production.POST("/defects/types/delete", s.DeleteDefectsTypes)      // {"id": int}
		production.POST("/defects/types/add", s.AddDefectsTypes)            // {"id": int}
		production.POST("/defects/add", s.AddDefects)                       // {"serial": string, "checkpoint_id": int, "defect_id": int}
		production.POST("/report/bydate/models/serial", s.GetByDateSerial)  // {"date1": string, "date2": string, "line": int}
		production.POST("/report/bydate", s.GetCountByDate)                 // {"date1": string, "date2": string, "line": int}
		production.POST("/report/bydate/models", s.GetByDateModels)         // {"date1": string, "date2": string, "line": int}
		production.POST("/report/remont", s.GetRemont)                      // {}
		production.POST("/report/remont/repairedcount", s.GetRepairedCount) // {}
		production.POST("/report/remont/today", s.GetRemontToday)           // {}
		production.POST("/report/remont/bydate", s.GetRemontByDate)         // {"date1": string, "date2": string}
		production.POST("/report/remont/update", s.UpdateRemont)            // {"name": string, "id": int} id-> defect id
		production.POST("/serial/input", s.SerialInput)                     // {"serial": string, "line": int}
		production.POST("/serial/info", s.GetInfoBySerial)                  // {"serial": string}
		production.POST("/galileo/todaymodels", s.GalileoTodayModels)       // {}
		production.POST("/models", s.Models)                                // {}
		production.POST("/metall/serial", s.MetallSerial)                   // {"id", int}
		production.POST("/vakum/serial", s.VakumSerial)                     // {"id", int}
		production.POST("/logistics", s.ProductionLogistics)                // {"line":int, "checkpoint_id":int, "serial": string}  //line-> income, checkpoint->outcome
		production.POST("/check_remont", s.CheckRemont)                     // {"serial": string}
		production.POST("/today/statistics", s.TodayStatistics)             // {"serial": string}
		production.POST("/plan/getcurrent", s.GetCurrentPlan)
		production.POST("/plan/today", s.GetPlanToday)
		// production.POST("/galileo/tcp", s.GalileoTCP)                      // {"id", int}
	}

	imports := s.Router.Group("/import")
	imports.Use(s.ImportCheckRole())
	{
		imports.POST("/lot/add", s.InsertLot)                                     //{"name": string, "comment": string, "token": string}
		imports.POST("/lot/delete", s.DeleteLot)                                  //{"lot_id": int, "token": string}
		imports.POST("/lot/update", s.UpdateLot)                                  //{"name": string, "comment": string, "lot_id": int, "token": string}
		imports.POST("/lot/block", s.BlockLot)                                    //{"lot_id": int, "token": string}
		imports.POST("/lot/unblock", s.UnBlockLot)                                //{"lot_id": int, "token": string}
		imports.POST("/lot/activate", s.ActivateLot)                              //{"lot_id": int, "token": string}
		imports.POST("/lot/deactivate", s.DeActivateLot)                          //{"lot_id": int, "token": string}
		imports.POST("/lot/getall", s.GetAllLot)                                  //{"token": string}
		imports.POST("/lot/getallactive", s.GetAllLotActive)                      //{"token": string}
		imports.POST("/batch/add", s.InsertBatch)                                 //{"lot_id": int, "name": string, "comment": string, "token": string}
		imports.POST("/batch/delete", s.DeleteBatch)                              //{"batch_id": int, "token": string}
		imports.POST("/batch/update", s.UpdateBatch)                              //{"name": string, "comment": string, "batch_id": int, "token": string}
		imports.POST("/batch/getbylot", s.GetBatchByLot)                          //{"lot_id":int, "token": string}
		imports.POST("/container/add", s.InsertContainer)                         //{"lot_id": int, "batch_id": int, "name": string, "comment": string, "token": string}
		imports.POST("/container/delete", s.DeleteContainer)                      //{"container_id": int, "token": string}
		imports.POST("/container/update", s.UpdateContainer)                      //{"name": string, "comment": string, "container_id": int, "token": string}
		imports.POST("/container/getbybatch", s.GetContainerByBatch)              //{"batch_id":int, "token": string}
		imports.POST("/container/components", s.GetContainerComponents)           //{"container_id":int, "token": string}
		imports.POST("/container/components/delete", s.ContainerComponentsDelete) //{"container_id":int, "token": string}
		imports.POST("/container/components/update", s.ContainerComponentsUpdate) //{"container_id":int, "token": string}
		imports.POST("/register/component", s.ImportIncomeRegister)               //{"lot_id": int, "batch_id":int, "container_id": int, "component_id": int, "quantity": float64, "comment": string, "token": string}
		imports.POST("/income/component", s.ImportIncomeAdd)                      //{"income_id": int, "quantity": float64, "token": string}
		imports.POST("/income/file", s.FileFromContainer)                         //{"income_id": int, "quantity": float64, "token": string}

	}

	s.Router.POST("galileo/input", s.GalileoInput)
}
