package main

import (
	"encoding/json"
	"fmt"
	"github.com/go-chi/chi"
	"log"
	"net/http"
	"os"
	"strconv"
	"time"
)

func HandleTodos(db *Database) http.HandlerFunc {
	return func(w http.ResponseWriter, req *http.Request) {
		w.Header().Set("Content-Type", "application/json")
		ctx := req.Context()
		size, sleep, transform, skipData := getRequestParams(req)

		var todos []Todo
		var err error

		// Fetch items
		if !skipData {
			todos, err = db.FetchTodos(ctx, size)
		}

		if err != nil {
			log.Println(err)
			// Handle error
			w.WriteHeader(http.StatusInternalServerError)
			_, _ = w.Write([]byte("Internal Server Error: " + err.Error()))
		} else {
			// OK
			if sleep > 0 {
				time.Sleep(time.Millisecond * time.Duration(sleep))
			}
			w.WriteHeader(http.StatusOK)

			var jsonEncodeErr error
			if transform {
				jsonEncodeErr = json.NewEncoder(w).Encode(transformTodos(todos))
			} else {
				jsonEncodeErr = json.NewEncoder(w).Encode(todos)
			}

			if jsonEncodeErr != nil {
				w.WriteHeader(http.StatusInternalServerError)
				_, _ = w.Write([]byte("error decoding " + jsonEncodeErr.Error()))
			}
		}
	}
}

func getRequestParams(req *http.Request) (size, sleep int, transform, skipData bool) {
	// Size
	sizeParam := req.URL.Query().Get("size")
	if sizeParam == "" {
		size = 20
	} else {
		if sizeValue, err := strconv.Atoi(sizeParam); err == nil {
			size = sizeValue
		}
	}

	// Sleep
	sleepParam := req.URL.Query().Get("sleep")
	if sleepParam == "" {
		sleep = 0
	} else {
		if sleepValue, err := strconv.Atoi(sleepParam); err == nil {
			sleep = sleepValue
		}
	}

	// Transform
	transformParam := req.URL.Query().Get("transform")
	if transformParam == "true" {
		transform = true
	}

	skipDataParam := req.URL.Query().Get("skipData")
	if skipDataParam == "true" {
		skipData = true
	}

	return
}

func getConfig() (dbUrl string, port int, err error) {
	dbUrl = os.Getenv("DB_URL")
	if dbUrl == "" {
		dbUrl = "postgresql://user:1234@localhost:5432/test?sslmode=disable"
	}

	portEnv := os.Getenv("PORT")
	if portEnv == "" {
		port = 3000
	} else {
		portValue, err := strconv.Atoi(portEnv)
		if err == nil {
			port = portValue
		}
	}

	return
}

func main() {
	dbUrl, port, err := getConfig()

	db, err := NewDB(dbUrl)
	if err != nil {
		log.Fatalln(err)
	}
	defer db.Close()

	r := chi.NewRouter()
	r.Get("/", func(w http.ResponseWriter, r *http.Request) {
		_, _ = w.Write([]byte("welcome"))
	})

	r.Get("/api/todos", HandleTodos(db))

	log.Println(fmt.Sprintf("Server started on port :%d", port))
	if err = http.ListenAndServe(fmt.Sprintf(":%d", port), r); err != nil {
		fmt.Println(err)
	}
}
